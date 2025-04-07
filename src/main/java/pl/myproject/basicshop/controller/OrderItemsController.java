package pl.myproject.basicshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.OrderItemsDTO;
import pl.myproject.basicshop.model.OrderItems;
import pl.myproject.basicshop.service.OrderItemsService;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemsDTO>> getAllOrderItems() {
        List<OrderItemsDTO> orderItems = orderItemsService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> getOrderItemById(@PathVariable Integer id) {
        OrderItemsDTO orderItem = orderItemsService.getOrderItemsById(id);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    public ResponseEntity<OrderItems> addOrderItems(@RequestBody OrderItems orderItems) {
        OrderItems savedOrderItems = orderItemsService.addOrderItems(orderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItems(@PathVariable Integer id) {
        orderItemsService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderItems> patchOrderItem(@PathVariable Integer id, @RequestBody OrderItems orderItems) {
        OrderItems updatedOrderItems = orderItemsService.updateOrderItem(id, orderItems);
        return ResponseEntity.ok(updatedOrderItems);
    }
}