package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

}