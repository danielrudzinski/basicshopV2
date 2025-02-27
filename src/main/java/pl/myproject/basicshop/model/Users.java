package pl.myproject.basicshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.Collection;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "users")

    List<Orders> orders;


    public Users() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
