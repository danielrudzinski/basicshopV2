package pl.myproject.basicshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myproject.basicshop.dto.UsersDTO;
import pl.myproject.basicshop.mapper.UsersMapper;
import pl.myproject.basicshop.model.Users;
import pl.myproject.basicshop.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Autowired
    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    public List<UsersDTO> getAllUsers() {
        List<UsersDTO> usersDTOs = StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .map(usersMapper)
                .collect(Collectors.toList());
        return usersDTOs;
    }

    public UsersDTO getUserById(Integer id) {
        return usersRepository.findById(id)
                .map(usersMapper::apply)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public Users addUsers(Users users) {
        if(isUserInvalid(users)) {
            throw new IllegalArgumentException("User data is invalid");
        }
        return usersRepository.save(users);
    }

    public void deleteUser(Integer id) {
        if(!usersRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        usersRepository.deleteById(id);
    }

    public Users updateUser(Integer id, Users user) {
        if(isUserInvalid(user)) {
            throw new IllegalArgumentException("User data is invalid");
        }

        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return usersRepository.save(existingUser);
    }

    public Users patchUser(Integer id, Users user) {
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if(user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if(user.getPassword() != null) existingUser.setPassword(user.getPassword());

        return usersRepository.save(existingUser);
    }

    private boolean isUserInvalid(Users user) {
        return user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty();
    }
}