package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateJobRequest {
    @NotNull(message = "title is can not null")
    private String title;

    @NotNull(message = "employerId is can not null")
    private Long employerId;

    @NotNull(message = "quantity is can not null")
    private Integer quantity;

    @NotNull(message = "description is can not null")
    private String description;

    @NotEmpty(message = "fieldIds is can not empty")
    private List<Integer> fieldIds;

    @NotEmpty(message = "provinceIds is can not empty")
    private List<Integer> provinceIds;

    @NotNull(message = "salary is can not null")
    private Integer salary;

    @NotNull(message = "expiredAt is can not null")
    private LocalDate expiredAt;

}
