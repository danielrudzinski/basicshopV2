package pl.myproject.basicshop.mapper;

import org.springframework.stereotype.Component;
import pl.myproject.basicshop.dto.OrderItemsDTO;
import pl.myproject.basicshop.dto.ProductsDTO;
import pl.myproject.basicshop.model.OrderItems;
import pl.myproject.basicshop.model.Products;

import java.util.function.Function;

@Component
public class OrderItemsMapper implements Function<OrderItems, OrderItemsDTO> {

    @Override
    public OrderItemsDTO apply(OrderItems orderItem) {
        if (orderItem == null) {
            return null;
        }

        Products product = orderItem.getProducts();
        ProductsDTO productDTO = new ProductsDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );

        return new OrderItemsDTO(product.getId(), orderItem.getQuantity(), productDTO);
    }
}
