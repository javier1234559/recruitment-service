package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Job;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobListResponse {
    private Long id;
    private String title;
    private Integer quantity;
    private Integer salary;
    private LocalDate expiredAt;
    private Long employerId;
    private String employerName;

    public static JobListResponse from(Job job ,String employerName) {

        return JobListResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .quantity(job.getQuantity())
                .salary(job.getSalary())
                .expiredAt(job.getExpiredAt())
                .employerId(job.getEmployer_id())
                .employerName(employerName)
                .build();
    }
}
