package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAll(Pageable page);
}