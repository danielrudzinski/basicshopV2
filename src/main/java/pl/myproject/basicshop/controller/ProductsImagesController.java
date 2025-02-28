package pl.myproject.basicshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.myproject.basicshop.model.ProductsImages;
import pl.myproject.basicshop.service.ProductsImagesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsImagesController {
    private final ProductsImagesService productsImagesService;

    public ProductsImagesController(ProductsImagesService productsImagesService) {
        this.productsImagesService = productsImagesService;
    }

    @GetMapping("/products/{productId}/images")
    public ResponseEntity<List<ProductsImages>> getProductImages(@PathVariable Integer productId) {
        return productsImagesService.getProductImages(productId);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer imageId) {
        return productsImagesService.getProductImage(imageId);
    }

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<?> uploadProductImage(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        return productsImagesService.uploadProductImage(productId, file);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer imageId) {
        return productsImagesService.deleteProductImage(imageId);
    }
}