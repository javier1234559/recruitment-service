package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query("SELECT em.email as result FROM Employer as em WHERE em.email =:email")
    String getExistEmail(@Param("email") String email);

    @Query("SELECT COUNT(em.province) as result FROM Employer as em WHERE em.province =:province")
    Integer getExistProvinceId(@Param("province") int province);
    @Query(value = "SELECT * FROM employer ORDER BY id LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<Employer> getListEmployerByPagination(@Param("offset") int offset, @Param("pageSize") int pageSize);
}