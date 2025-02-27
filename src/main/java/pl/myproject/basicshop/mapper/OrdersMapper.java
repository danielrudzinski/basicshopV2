package pl.myproject.basicshop.mapper;

import org.springframework.stereotype.Component;
import pl.myproject.basicshop.dto.OrdersDTO;
import pl.myproject.basicshop.dto.UsersDTO;
import pl.myproject.basicshop.model.Orders;
import pl.myproject.basicshop.model.Users;

import java.util.function.Function;
@Component
public class OrdersMapper implements Function<Orders, OrdersDTO> {

    @Override
    public OrdersDTO apply(Orders orders) {
        if (orders == null) {
            return null;
        }


        Users user = orders.getUsers();
        UsersDTO userDTO = new UsersDTO(user.getId(), user.getEmail());

        return new OrdersDTO(
                orders.getId(),
                userDTO
        );
    }
}