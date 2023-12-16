package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.dto.SeekerDto;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.JobProvince;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT DISTINCT new vn.unigap.api.dto.SeekerDto(s.id, s.name) " +
            "FROM Seeker s JOIN Resume r ON s.id = r.seeker_id WHERE r.salary <= :salary")
    List<SeekerDto> listRelatedSeekerBySalary(@Param("salary") Integer salary);

    Page<Job> findAll(Pageable page);
}