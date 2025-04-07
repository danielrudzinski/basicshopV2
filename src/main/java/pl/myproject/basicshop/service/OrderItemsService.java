package pl.myproject.basicshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.OrderItemsDTO;
import pl.myproject.basicshop.mapper.OrderItemsMapper;
import pl.myproject.basicshop.model.OrderItems;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.repository.OrderItemsRepository;
import pl.myproject.basicshop.repository.OrdersRepository;
import pl.myproject.basicshop.repository.ProductsRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemsMapper orderItemsMapper;

    public OrderItemsService(OrderItemsRepository orderItemsRepository, ProductsRepository productsRepository, OrdersRepository ordersRepository, OrderItemsMapper orderItemsMapper) {
        this.orderItemsRepository = orderItemsRepository;
        this.productsRepository = productsRepository;
        this.ordersRepository = ordersRepository;
        this.orderItemsMapper = orderItemsMapper;
    }

    public List<OrderItemsDTO> getAllOrderItems() {
        List<OrderItemsDTO> orderItemsDTOs = StreamSupport.stream(orderItemsRepository.findAll().spliterator(), false)
                .map(orderItemsMapper)
                .collect(Collectors.toList());

        return orderItemsDTOs;
    }

    public OrderItemsDTO getOrderItemsById(Integer id) {
        return orderItemsRepository.findById(id)
                .map(orderItemsMapper::apply)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found with id: " + id));
    }

    public OrderItems addOrderItems(OrderItems orderItems) {
        if (orderItems == null || orderItems.getOrders() == null || orderItems.getProducts() == null) {
            throw new IllegalArgumentException("Order items data is invalid");
        }

        Integer productId = orderItems.getProducts().getId();
        Integer orderQuantity = orderItems.getQuantity();

        if (productId == null || orderQuantity == null || orderQuantity <= 0) {
            throw new IllegalArgumentException("Product ID or quantity is invalid");
        }

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        if (product.getStock() < orderQuantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + productId);
        }

        product.setStock(product.getStock() - orderQuantity);
        productsRepository.save(product);

        return orderItemsRepository.save(orderItems);
    }

    public OrderItems updateOrderItem(Integer id, OrderItems updatedOrderItems) {
        OrderItems existingItem = orderItemsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found with id: " + id));

        if (updatedOrderItems.getQuantity() != null && updatedOrderItems.getQuantity() > 0) {
            existingItem.setQuantity(updatedOrderItems.getQuantity());
        }

        if (updatedOrderItems.getProducts() != null && updatedOrderItems.getProducts().getId() != null) {
            Products product = productsRepository.findById(updatedOrderItems.getProducts().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingItem.setProducts(product);
        }

        return orderItemsRepository.save(existingItem);
    }

    public void deleteOrderItem(Integer id) {
        if (!orderItemsRepository.existsById(id)) {
            throw new EntityNotFoundException("Order item not found with id: " + id);
        }
        orderItemsRepository.deleteById(id);
    }
}