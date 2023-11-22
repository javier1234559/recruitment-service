package vn.unigap.api.dto.in;

import jakarta.validation.constraints.*;
import lombok.Data;

//https://reflectoring.io/bean-validation-with-spring-boot/
//https://dev.to/tericcabrel/validate-request-body-and-parameter-in-spring-boot-1fai
//https://viblo.asia/p/dung-validation-bang-tay-trong-spring-boot-phan-1-3P0lPGyoZox

@Data
public class EmployerDtoIn {

    @Email
    @NotEmpty
    @Size(max = 255, message = "Email length should not be greater than 255")
    private String email;

    @NotEmpty
    @Size(max = 255, message = "Name length should not be greater than 255")
    private String name;

    @NotNull
    private Integer provinceId;

    private String description;

}
