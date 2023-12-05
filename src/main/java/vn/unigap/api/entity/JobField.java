package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobField {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
}
