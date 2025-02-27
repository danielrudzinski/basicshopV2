package pl.myproject.basicshop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.mapper.OrdersMapper;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.repository.OrdersRepository;
import pl.myproject.basicshop.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final OrdersMapper ordersMapper;

    public OrdersService(OrdersRepository ordersRepository, UsersRepository usersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.ordersMapper = ordersMapper;
    }

    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        Iterable<Orders> orders = ordersRepository.findAll();
        List<OrdersDTO> ordersDTOs = StreamSupport.stream(orders.spliterator(),false)
                .map(ordersMapper::apply)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordersDTOs);
    }
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Integer id) {
        return ordersRepository.findById(id)
                .map(ordersMapper::apply)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    public ResponseEntity<Orders> addOrders(@RequestBody Orders orders) {
        if (isOrderInvalid(orders)) {
            return ResponseEntity.badRequest().build();
        }
        Orders savedOrders = ordersRepository.save(orders);
        UriComponents uriComponents = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrders.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
    public ResponseEntity<Void>deleteOrders(@PathVariable Integer id ){
        if(!ordersRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        ordersRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders updatedOrder) {
        return ordersRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setUsers(updatedOrder.getUsers());

                    return ordersRepository.save(existingOrder);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Orders> patchOrder(@PathVariable Integer id, @RequestBody Orders updatedOrder) {
        return ordersRepository.findById(id)
                .map(existingOrder -> {
                    if (updatedOrder.getUsers() != null) existingOrder.setUsers(updatedOrder.getUsers());

                    return ordersRepository.save(existingOrder);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    private boolean isOrderInvalid(Orders order) {
        return order.getUsers() == null;
    }

}
