package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdateResumeRequest {
    @NotNull(message = "id is can not null")
    private Long id;

    @NotNull(message = "careerObj is can not null")
    private String careerObj;

    @NotNull(message = "title is can not null")
    private String title;

    @NotNull(message = "salary is can not null")
    private Integer salary;

    @NotEmpty(message = "fieldIds is can not empty")
    private List<Integer> fieldIds;

    @NotEmpty(message = "provinceIds is can not empty")
    private List<Integer> provinceIds;

}
