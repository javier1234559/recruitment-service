package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Optional<Employer> findByEmail(String email);

    Page<Employer> findAll(Pageable page);

//    @Query("SELECT p FROM Province p WHERE p.id = :provinceId")
//    Optional<Province> findProvinceById(@Param("provinceId") Long provinceId);
//    @Query("SELECT em.email as result FROM Employer as em WHERE em.email =:email")
//    String getExistEmail(@Param("email") String email);

//    @Query(value = "SELECT * FROM employer ORDER BY id LIMIT :pageSize OFFSET :offset", nativeQuery = true)
//    List<Employer> getListEmployerByPagination(@Param("offset") int offset, @Param("pageSize") int pageSize);
}