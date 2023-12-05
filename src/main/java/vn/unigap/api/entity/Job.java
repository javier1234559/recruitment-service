package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Job {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long employer_id ;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private Integer salary;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JobField> fields = new ArrayList<>();

    private List<JobProvince> provinces = new ArrayList<>();

    @Column(name = "updated_at")
    private Date updatedAt= new Date();

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "expired_at")
    private Date expiredAt;
}
