package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Seeker;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeekerListResponse {
    private Long id;
    private String name;
    private String birthday;
    private String address;
    private Integer provinceId;
    private String provinceName;

    public static SeekerListResponse from(Seeker seeker, String provinceName) {
        return SeekerListResponse.builder()
                .id(seeker.getId())
                .name(seeker.getName())
                .address(seeker.getAddress())
                .birthday(seeker.getBirthday())
                .provinceId(seeker.getProvince())
                .provinceName(provinceName)
                .build();
    }
}
