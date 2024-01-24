package vn.unigap.api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.jpa.Seeker;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, Long> {

}