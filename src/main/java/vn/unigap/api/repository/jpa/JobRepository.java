package vn.unigap.api.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.SeekerDto;
import vn.unigap.api.entity.jpa.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT DISTINCT new vn.unigap.api.dto.SeekerDto(s.id, s.name) " +
            "FROM Seeker s JOIN Resume r ON s.id = r.seeker_id WHERE r.salary <= :salary")
    List<SeekerDto> listRelatedSeekerBySalary(@Param("salary") Integer salary);

    Page<Job> findAll(Pageable page);
}