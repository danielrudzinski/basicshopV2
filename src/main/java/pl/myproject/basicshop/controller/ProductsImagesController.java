package pl.myproject.basicshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.myproject.basicshop.service.ProductsImagesService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductsImagesController {

    private final ProductsImagesService productsImagesService;

    public ProductsImagesController(ProductsImagesService productsImagesService) {
        this.productsImagesService = productsImagesService;
    }

    // Endpoint do uploadu obrazu
    @PostMapping("/products/{productId}/images")
    public ResponseEntity<String> uploadProductImage(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = productsImagesService.uploadProductImage(productId, file);
            return ResponseEntity.ok("Image uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }

    // Endpoint do pobrania URL do obrazu produktu
    @GetMapping("/products/{productId}/image")
    public ResponseEntity<String> getProductImage(@PathVariable Integer productId) {
        String imageUrl = productsImagesService.getProductImageUrl(productId);
        return ResponseEntity.ok(imageUrl);
    }
}
