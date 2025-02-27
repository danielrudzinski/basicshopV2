package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.UsersDTO;
import pl.myproject.basicshop.model.Users;
import pl.myproject.basicshop.service.UsersService;

import java.util.List;

@RestController
public class UsersController {
    private final UsersService usersService;
    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("users")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        return usersService.getAllUsers();
    }
    @PostMapping("users")
    public ResponseEntity<Users> addUsers(@RequestBody Users user){
        return usersService.addUsers(user);
    }
    @GetMapping("users/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Integer id){
        return usersService.getUserById(id);
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        return usersService.deleteUser(id);
    }
    @PutMapping("users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users user){
        return usersService.updateUser(id, user);
    }
    @PatchMapping("users/{id}")
    public ResponseEntity<Users> patchUser(@PathVariable Integer id, @RequestBody Users user){
        return usersService.patchUser(id, user);
    }



}
