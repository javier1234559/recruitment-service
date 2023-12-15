package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.entity.Resume;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeOneResponse {
    private Long id;
    private Long seekerId;
    private String seekerName;
    private String careerObj;
    private String title;
    private Integer salary;
    private List<JobFieldDto> fields;
    private List<JobProvinceDto> provinces;

    public static ResumeOneResponse from(Resume resume, String seekerName, List<JobFieldDto> fieldIds, List<JobProvinceDto> provinceIds) {
        return ResumeOneResponse.builder()
                .id(resume.getId())
                .seekerId(resume.getId())
                .seekerName(seekerName)
                .careerObj(resume.getCareer_obj())
                .title(resume.getTitle())
                .salary(resume.getSalary())
                .fields(fieldIds)
                .provinces(provinceIds)
                .build();
    }
}
