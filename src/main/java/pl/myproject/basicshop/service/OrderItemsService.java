package pl.myproject.basicshop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.myproject.basicshop.dto.OrderItemsDTO;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.mapper.OrderItemsMapper;
import pl.myproject.basicshop.mapper.OrdersMapper;
import pl.myproject.basicshop.model.OrderItems;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.repository.OrderItemsRepository;
import pl.myproject.basicshop.repository.OrdersRepository;
import pl.myproject.basicshop.repository.ProductsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public ResponseEntity<List<OrderItemsDTO>> getAllOrderItems() {
        List<OrderItemsDTO> orderItemsDTOs = StreamSupport.stream(orderItemsRepository.findAll().spliterator(), false)
                .map(orderItemsMapper)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderItemsDTOs);
    }

    public ResponseEntity<OrderItemsDTO> getOrderItemsById(@PathVariable Integer id) {
        return orderItemsRepository.findById(id)
                .map(orderItemsMapper::apply)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    public ResponseEntity<OrderItems> addOrderItems(@RequestBody OrderItems orderItems) {
        try {
            if (orderItems == null || orderItems.getOrders() == null || orderItems.getProducts() == null) {
                return ResponseEntity.badRequest().build();
            }

            Integer productId = orderItems.getProducts().getId();
            Integer orderQuantity = orderItems.getQuantity();

            if (productId == null || orderQuantity == null || orderQuantity <= 0) {
                return ResponseEntity.badRequest().build();
            }


            Optional<Products> optionalProduct = productsRepository.findById(productId);
            if (optionalProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Products product = optionalProduct.get();


            if (product.getStock() < orderQuantity) {
                return ResponseEntity.badRequest().body(null);
            }


            product.setStock(product.getStock() - orderQuantity);
            productsRepository.save(product);


            OrderItems savedOrderItems = orderItemsRepository.save(orderItems);
            UriComponents uriComponents = ServletUriComponentsBuilder.
                    fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedOrderItems.getId());

            return ResponseEntity.created(uriComponents.toUri()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<OrderItems> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItems updatedOrderItems) {
        return orderItemsRepository.findById(id)
                .map(existingItem -> {
                    if (updatedOrderItems.getQuantity() != null && updatedOrderItems.getQuantity() > 0) {
                        existingItem.setQuantity(updatedOrderItems.getQuantity());
                    }

                    if (updatedOrderItems.getProducts() != null && updatedOrderItems.getProducts().getId() != null) {
                        productsRepository.findById(updatedOrderItems.getProducts().getId()).ifPresent(existingItem::setProducts);
                    }

                    orderItemsRepository.save(existingItem);
                    return ResponseEntity.ok(existingItem);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer id) {
        if (!orderItemsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderItemsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

