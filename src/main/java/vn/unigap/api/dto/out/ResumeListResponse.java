package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Resume;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeListResponse {
    private Long id;
    private Long seekerId;
    private String seekerName;
    private String careerObj;
    private String title;
    private Integer salary;

    public static ResumeListResponse from(Resume resume, String seekerName) {
        return ResumeListResponse.builder()
                .id(resume.getId())
                .seekerId(resume.getId())
                .seekerName(seekerName)
                .careerObj(resume.getCareer_obj())
                .title(resume.getTitle())
                .salary(resume.getSalary())
                .build();
    }
}
