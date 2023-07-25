package org.example.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data @Entity @Table(name = "tbl_categories") @NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date created_at;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
