package org.example.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

