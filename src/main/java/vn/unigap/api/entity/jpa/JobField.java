package vn.unigap.api.entity.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "job_field")
public class JobField {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;
}
