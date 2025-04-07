package pl.myproject.basicshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.myproject.basicshop.service.ProductsImagesService;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductsImagesController {

    private final ProductsImagesService productsImagesService;

    public ProductsImagesController(ProductsImagesService productsImagesService) {
        this.productsImagesService = productsImagesService;
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<String> uploadProductImage(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = productsImagesService.uploadProductImage(productId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<String> getProductImage(@PathVariable Integer productId) {
        String imageUrl = productsImagesService.getProductImageUrl(productId);
        return ResponseEntity.ok(imageUrl);
    }
}