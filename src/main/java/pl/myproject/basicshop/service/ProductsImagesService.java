package pl.myproject.basicshop.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.myproject.basicshop.model.Products;
import pl.myproject.basicshop.model.ProductsImages;
import pl.myproject.basicshop.repository.ProductsImagesRepository;
import pl.myproject.basicshop.repository.ProductsRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsImagesService {
    private final ProductsImagesRepository productsImagesRepository;
    private final ProductsRepository productsRepository;

    public ProductsImagesService(ProductsImagesRepository productsImagesRepository, ProductsRepository productsRepository) {
        this.productsImagesRepository = productsImagesRepository;
        this.productsRepository = productsRepository;
    }

    public ResponseEntity<List<ProductsImages>> getProductImages(@PathVariable Integer productId) {
        Optional<Products> productOptional = productsRepository.findById(productId);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductsImages> images = productsImagesRepository.findByProductsId(productId);
        return ResponseEntity.ok(images);
    }

    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer imageId) {
        Optional<ProductsImages> imageOptional = productsImagesRepository.findById(imageId);

        if (imageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProductsImages image = imageOptional.get();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());
    }

    public ResponseEntity<?> uploadProductImage(@PathVariable Integer productId, MultipartFile file) {
        try {
            Optional<Products> productOptional = productsRepository.findById(productId);

            if (productOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Products product = productOptional.get();

            ProductsImages image = new ProductsImages();
            image.setName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setData(file.getBytes());
            image.setProducts(product);

            ProductsImages savedImage = productsImagesRepository.save(image);

            UriComponents uriComponents = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedImage.getId());

            return ResponseEntity.created(uriComponents.toUri()).build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to store file: " + e.getMessage());
        }
    }

    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer imageId) {
        if (!productsImagesRepository.existsById(imageId)) {
            return ResponseEntity.notFound().build();
        }

        productsImagesRepository.deleteById(imageId);
        return ResponseEntity.noContent().build();
    }
}