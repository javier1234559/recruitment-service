package vn.unigap.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.StatisticElementDto;
import vn.unigap.api.dto.out.StatisticResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//https://www.codejava.net/frameworks/spring-boot/spring-data-jpa-custom-repository-example

@Repository
public class StatisticRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public StatisticResponse getStatisticByDate(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime startOfDay = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(toDate, LocalTime.MAX);

        Long numEmployers = (Long) entityManager.createQuery("SELECT COUNT(e.id) FROM Employer e WHERE e.createdAt BETWEEN :start AND :end")
                .setParameter("start", startOfDay.toLocalDate())
                .setParameter("end", endOfDay.toLocalDate())
                .getSingleResult();

        Long numSeekers = (Long) entityManager.createQuery("SELECT COUNT(s.id) FROM Seeker s WHERE s.createdAt BETWEEN :start AND :end")
                .setParameter("start", startOfDay.toLocalDate())
                .setParameter("end", endOfDay.toLocalDate())
                .getSingleResult();

        Long numResumes = (Long) entityManager.createQuery("SELECT COUNT(r.id) FROM Resume r WHERE r.createdAt BETWEEN :start AND :end")
                .setParameter("start", startOfDay.toLocalDate())
                .setParameter("end", endOfDay.toLocalDate())
                .getSingleResult();

        Long numJobs = (Long) entityManager.createQuery("SELECT COUNT(j.id) FROM Job j WHERE j.createdAt BETWEEN :start AND :end")
                .setParameter("start", startOfDay.toLocalDate())
                .setParameter("end", endOfDay.toLocalDate())
                .getSingleResult();

        List<StatisticElementDto> list = getListElement(fromDate, toDate);

        return StatisticResponse.builder()
                .numEmployer(numEmployers)
                .numJob(numJobs)
                .numSeeker(numSeekers)
                .numResume(numResumes)
                .chart(list)
                .build();
    }

    public List<StatisticElementDto> getListElement(LocalDate fromDate, LocalDate toDate) {

        List<StatisticElementDto> resultList = new ArrayList<>();
        for (LocalDate date = fromDate; date.isBefore(toDate); date = date.plusDays(1)) {

            Long numEmployer = (Long) entityManager.createQuery(
                            "SELECT COUNT(s.id) FROM Employer s WHERE s.createdAt = :date")
                    .setParameter("date", date).getSingleResult();

            Long numJob = (Long) entityManager.createQuery(
                            "SELECT COUNT(s.id) FROM Job s WHERE s.createdAt = :date")
                    .setParameter("date", date).getSingleResult();

            Long numSeeker = (Long) entityManager.createQuery(
                            "SELECT COUNT(s.id) FROM Seeker s WHERE s.createdAt = :date")
                    .setParameter("date", date).getSingleResult();

            Long numResume = (Long) entityManager.createQuery(
                            "SELECT COUNT(s.id) FROM Resume s WHERE s.createdAt = :date")
                    .setParameter("date", date).getSingleResult();

            resultList.add(StatisticElementDto.builder()
                    .date(date)
                    .numEmployer(numEmployer)
                    .numJob(numJob)
                    .numSeeker(numSeeker)
                    .numResume(numResume).build());
        }

        return resultList;
    }
}
