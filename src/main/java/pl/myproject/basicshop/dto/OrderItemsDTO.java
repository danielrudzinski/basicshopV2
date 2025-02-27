package pl.myproject.basicshop.dto;

public record OrderItemsDTO(Integer product_id, Integer quantity, ProductsDTO productDTO) {

}
