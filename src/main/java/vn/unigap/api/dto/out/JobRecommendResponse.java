package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.dto.SeekerDto;
import vn.unigap.api.entity.jpa.Job;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRecommendResponse {
    private Long id;
    private String title;
    private Integer quantity;
    private List<JobFieldDto> fieldIds;
    private List<JobProvinceDto> provinceIds;
    private Integer salary;
    private LocalDate expiredAt;
    private Long employerId;
    private String employerName;
    private List<SeekerDto> seekers;

    public static JobRecommendResponse from(Job job, List<JobFieldDto> fieldIds, List<JobProvinceDto> provinceIds, String employerName, List<SeekerDto> seekers) {

        return JobRecommendResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .quantity(job.getQuantity())
                .fieldIds(fieldIds)
                .provinceIds(provinceIds)
                .salary(job.getSalary())
                .expiredAt(job.getExpiredAt())
                .employerId(job.getEmployer_id())
                .employerName(employerName)
                .seekers(seekers)
                .build();
    }
}
