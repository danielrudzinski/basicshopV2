package pl.myproject.basicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myproject.basicshop.model.Products;
@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
