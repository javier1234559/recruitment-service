package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.unigap.common.validation.DateString;

@Data
@AllArgsConstructor
public class CreateSeekerRequest {

    @NotNull(message = "name is can not null")
    private String name;

    @NotNull(message = "birthday is can not null")
    @DateString()
    private String birthday;

    private String address;

    @NotNull(message = "provinceId is can not empty")
    private Integer provinceId;
}
