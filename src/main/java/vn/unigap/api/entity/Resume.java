package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resume {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seeker_id")
    private Long seeker_id;

    private String career_obj;

    @Column(name = "title")
    private String title;

    @Column(name = "salary")
    private Integer salary;

    @ManyToMany
    @JoinTable(
            name = "job_province_join",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "province_id")
    )
    private Set<JobProvince> provinces = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "job_fields_join",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "fields_id")
    )
    private Set<JobField> fields = new HashSet<>();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Column(name = "created_at")
    private Date createdAt = new Date();

}
