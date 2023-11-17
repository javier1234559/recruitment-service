package vn.unigap.api.dto.out;

import lombok.Data;

@Data
public class EmployerDtoOut {
    private long id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;
    private String description;

}