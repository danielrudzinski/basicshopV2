package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        List<OrdersDTO> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable int id) {
        OrdersDTO order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders) {
        Orders savedOrder = ordersService.addOrders(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Integer id) {
        ordersService.deleteOrders(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable Integer id, @RequestBody Orders orders) {
        Orders updatedOrder = ordersService.updateOrder(id, orders);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Orders> patchOrders(@PathVariable Integer id, @RequestBody Orders orders) {
        Orders patchedOrder = ordersService.patchOrder(id, orders);
        return ResponseEntity.ok(patchedOrder);
    }
}