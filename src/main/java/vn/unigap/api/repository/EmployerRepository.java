package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {


}