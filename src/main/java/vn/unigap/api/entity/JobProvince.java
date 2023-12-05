package vn.unigap.api.entity;


import jakarta.persistence.*;
import lombok.Data;

//https://gist.github.com/jahe/18a4efe614fc73cf184d8ceef8cdc996
//https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html
//https://docs.spring.io/spring-data/jpa/docs/current/api/
//https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#entities

@Entity
@Data
public class JobProvince {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;
}
