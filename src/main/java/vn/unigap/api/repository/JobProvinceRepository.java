package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.JobProvince;

@Repository
public interface JobProvinceRepository extends JpaRepository<JobProvince, Integer> {
}
