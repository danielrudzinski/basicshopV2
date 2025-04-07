package pl.myproject.basicshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.ProductsDTO;
import pl.myproject.basicshop.mapper.ProductsMapper;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.repository.ProductsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    public ProductsService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    public List<ProductsDTO> getAllProducts() {
        List<ProductsDTO> productsDTOs = productsRepository.findAll().stream()
                .map(productsMapper::apply)
                .collect(Collectors.toList());
        return productsDTOs;
    }

    public ProductsDTO getProductById(Integer id) {
        return productsRepository.findById(id)
                .map(productsMapper::apply)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public Products addProducts(Products products) {
        if(isProductInvalid(products)) {
            throw new IllegalArgumentException("Product data is invalid");
        }
        return productsRepository.save(products);
    }

    public void deleteProducts(Integer id) {
        if(!productsRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }

        productsRepository.deleteById(id);
    }

    public Products updateProducts(Integer id, Products updatedProduct) {
        Products existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());

        return productsRepository.save(existingProduct);
    }

    public Products patchProducts(Integer id, Products updatedProduct) {
        Products existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (updatedProduct.getName() != null) existingProduct.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existingProduct.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getPrice() != null) existingProduct.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getStock() != null) existingProduct.setStock(updatedProduct.getStock());

        return productsRepository.save(existingProduct);
    }

    private boolean isProductInvalid(Products products) {
        return products.getName() == null || products.getName().isEmpty() ||
                products.getPrice() == null || products.getPrice() <= 0 ||
                products.getStock() == null || products.getStock() <= 0;
    }
}