package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "employer_id")
    private Long employer_id;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "fields")
    private String fields;

    @Column(name = "provinces")
    private String provinces;

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "expired_at")
    private Date expiredAt;
}
