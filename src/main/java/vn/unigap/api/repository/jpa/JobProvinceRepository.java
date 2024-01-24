package vn.unigap.api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.jpa.JobProvince;

@Repository
public interface JobProvinceRepository extends JpaRepository<JobProvince, Integer> {
}
