package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.dto.StatisticElementDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticResponse {
    @Builder.Default
    private Long numEmployer = 0L;
    @Builder.Default
    private Long numJob = 0L;
    @Builder.Default
    private Long numSeeker = 0L;
    @Builder.Default
    private Long numResume = 0L;
    @Builder.Default
    private List<StatisticElementDto> chart = new ArrayList<>();

}
