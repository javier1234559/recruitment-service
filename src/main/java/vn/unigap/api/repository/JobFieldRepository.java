package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.JobField;

@Repository
public interface JobFieldRepository extends JpaRepository<JobField, Integer> {

}
