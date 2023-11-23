package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.Employer;

//https://howtodoinjava.com/lombok/lombok-builder-annotation/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut {
    private long id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;
    private String description;

    public static EmployerDtoOut from(Employer e, String provinceName) {
        return EmployerDtoOut.builder()
                .id(e.getId())
                .email(e.getEmail())
                .name(e.getName())
                .provinceId(e.getProvince())
                .provinceName(provinceName)
                .description(e.getDescription())
                .build();
    }
}
