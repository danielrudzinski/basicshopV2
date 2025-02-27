package pl.myproject.basicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myproject.basicshop.model.OrderItems;
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}
