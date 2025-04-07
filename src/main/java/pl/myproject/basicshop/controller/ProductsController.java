package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.ProductsDTO;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.service.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        List<ProductsDTO> products = productsService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable Integer id) {
        ProductsDTO product = productsService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Products> addProduct(@RequestBody Products products) {
        Products savedProduct = productsService.addProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productsService.deleteProducts(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Integer id, @RequestBody Products product) {
        Products updatedProduct = productsService.updateProducts(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Products> patchProduct(@PathVariable Integer id, @RequestBody Products product) {
        Products patchedProduct = productsService.patchProducts(id, product);
        return ResponseEntity.ok(patchedProduct);
    }
}