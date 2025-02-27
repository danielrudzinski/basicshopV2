package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.service.OrdersService;

import java.util.List;

@RestController
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
    @GetMapping("orders")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
       return ordersService.getAllOrders();
    }
    @GetMapping("orders/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable int id) {
        return ordersService.getOrderById(id);
    }
    @PostMapping("orders")
    public ResponseEntity<Orders>addOrder(@RequestBody Orders orders) {
        return ordersService.addOrders(orders);
    }
    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Integer id) {
        return ordersService.deleteOrders(id);
    }
    @PutMapping("orders/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable Integer id, @RequestBody Orders orders) {
        return ordersService.updateOrder(id, orders);
    }

    @PatchMapping("orders/{id}")
    public ResponseEntity<Orders> patchOrders(@PathVariable Integer id, @RequestBody Orders orders) {
        return ordersService.patchOrder(id, orders);
    }

}
