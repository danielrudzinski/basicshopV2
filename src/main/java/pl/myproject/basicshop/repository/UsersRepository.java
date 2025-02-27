package pl.myproject.basicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myproject.basicshop.model.Users;
@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
