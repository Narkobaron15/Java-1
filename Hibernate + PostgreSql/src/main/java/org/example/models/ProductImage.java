package org.example.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tbl_product_images")
@Data
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority")
    private int priority;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
