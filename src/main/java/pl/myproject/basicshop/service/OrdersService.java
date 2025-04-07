package pl.myproject.basicshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.mapper.OrdersMapper;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.repository.OrdersRepository;
import pl.myproject.basicshop.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
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

    public List<OrdersDTO> getAllOrders() {
        Iterable<Orders> orders = ordersRepository.findAll();
        List<OrdersDTO> ordersDTOs = StreamSupport.stream(orders.spliterator(), false)
                .map(ordersMapper::apply)
                .collect(Collectors.toList());
        return ordersDTOs;
    }

    public OrdersDTO getOrderById(Integer id) {
        return ordersRepository.findById(id)
                .map(ordersMapper::apply)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public Orders addOrders(Orders orders) {
        if (isOrderInvalid(orders)) {
            throw new IllegalArgumentException("Order data is invalid");
        }
        return ordersRepository.save(orders);
    }

    public void deleteOrders(Integer id) {
        if (!ordersRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }

        ordersRepository.deleteById(id);
    }

    public Orders updateOrder(Integer id, Orders updatedOrder) {
        Orders existingOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        existingOrder.setUsers(updatedOrder.getUsers());

        return ordersRepository.save(existingOrder);
    }

    public Orders patchOrder(Integer id, Orders updatedOrder) {
        Orders existingOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        if (updatedOrder.getUsers() != null) {
            existingOrder.setUsers(updatedOrder.getUsers());
        }

        return ordersRepository.save(existingOrder);
    }

    private boolean isOrderInvalid(Orders order) {
        return order.getUsers() == null;
    }
}