package pl.myproject.basicshop.controller;

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
        return orderItemsService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> getOrderItemById(@PathVariable Integer id) {
        return orderItemsService.getOrderItemsById(id);
    }

    @PostMapping
    public ResponseEntity<OrderItems> addOrderItems(@RequestBody OrderItems orderItems) {
        return orderItemsService.addOrderItems(orderItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItems(@PathVariable Integer id) {
        return orderItemsService.deleteOrderItem(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderItems> patchOrderItem(@PathVariable Integer id, @RequestBody OrderItems orderItems) {
        return orderItemsService.updateOrderItem(id, orderItems);
    }

}