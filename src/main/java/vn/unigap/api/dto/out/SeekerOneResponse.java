package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.Seeker;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeekerOneResponse {
    private Long id;
    private String name;
    private String birthday;
    private String address;
    private Integer provinceId;
    private String provinceName;

    public static SeekerOneResponse from(Seeker seeker, String provinceName) {
        return SeekerOneResponse.builder()
                .id(seeker.getId())
                .name(seeker.getName())
                .address(seeker.getAddress())
                .birthday(seeker.getBirthday())
                .provinceId(seeker.getProvince())
                .provinceName(provinceName)
                .build();
    }
}
