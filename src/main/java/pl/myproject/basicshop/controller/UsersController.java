package pl.myproject.basicshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myproject.basicshop.dto.UsersDTO;
import pl.myproject.basicshop.model.Users;
import pl.myproject.basicshop.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Integer id){
        UsersDTO user = usersService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Users> addUsers(@RequestBody Users user){
        Users savedUser = usersService.addUsers(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users user){
        Users updatedUser = usersService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Users> patchUser(@PathVariable Integer id, @RequestBody Users user){
        Users patchedUser = usersService.patchUser(id, user);
        return ResponseEntity.ok(patchedUser);
    }
}