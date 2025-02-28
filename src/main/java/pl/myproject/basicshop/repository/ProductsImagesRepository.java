package pl.myproject.basicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.myproject.basicshop.model.ProductsImages;

import java.util.List;

public interface ProductsImagesRepository extends JpaRepository<ProductsImages, Integer> {
    List<ProductsImages> findByProductsId(Integer productId);
}
