package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data @Entity @NoArgsConstructor @Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, nullable = false, length = 256)
    private String email;

    @Column(unique = true, nullable = false, length = 25)
    private String phone;

    @Column(nullable = false, length = 256)
    private String password;

    // Constructor
    public User(String username, String firstName, String lastName, String email, String phone, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "tbl_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(firstName).append(" ")
                .append(lastName)
                .append(" (ID = ").append(id)
                .append(", Username = ").append(username)
                .append(", Email = ").append(email)
                .append(", Phone = ").append(phone)
                .append(")");
        return builder.toString();
    }
}
