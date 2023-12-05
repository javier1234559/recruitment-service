package vn.unigap.api.dto.in;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEmployerDtoIn {

    @NotEmpty
    @Size(max = 255, message = "Name length should not be greater than 255")
    private String name;

    @NotNull
    @Min(value = 1, message = "Province ID must be at least 1")
    private Integer provinceId;

    private String description;

}
