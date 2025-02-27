package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.ProductsDTO;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.service.ProductsService;

import java.util.List;

@RestController
public class ProductsController {
    @Autowired
    private ProductsService productsService;
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("products")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        return productsService.getAllProducts();
    }
    @GetMapping("products/{id}")
    public ResponseEntity<ProductsDTO> getUserById(@PathVariable Integer id) {
        return productsService.getProductsrById(id);
    }
    @PostMapping("products")
    public ResponseEntity<Products> addProduct(@RequestBody Products products) {
        return productsService.addProducts(products);
    }
    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return productsService.deleteProducts(id);
    }
    @PutMapping("products/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Integer id, @RequestBody Products product) {
        return productsService.updateProducts(id, product);
    }

    @PatchMapping("products/{id}")
    public ResponseEntity<Products> patchProduct(@PathVariable Integer id, @RequestBody Products product) {
        return productsService.patchProducts(id, product);
    }

}
