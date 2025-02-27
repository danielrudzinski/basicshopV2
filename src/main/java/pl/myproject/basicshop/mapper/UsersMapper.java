package pl.myproject.basicshop.mapper;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.UsersDTO;
import pl.myproject.basicshop.model.Users;

import java.util.function.Function;

@Component
public class UsersMapper implements Function<Users, UsersDTO> {

    @Override
    public UsersDTO apply(Users user) {
        if (user == null) {
            return null;
        }
        return new UsersDTO
                (       user.getId(),
                        user.getEmail());
    }
}
