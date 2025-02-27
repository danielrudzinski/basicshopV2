package pl.myproject.basicshop.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.ProductsDTO;
import pl.myproject.basicshop.model.Products;

import java.util.function.Function;
@Component
public class ProductsMapper implements Function<Products, ProductsDTO> {
    @Override
    public ProductsDTO apply(Products product) {
        if (product == null) {
            return null;
        }
        return new ProductsDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()

        );
    }
}
