package pl.myproject.basicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myproject.basicshop.model.Orders;
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
