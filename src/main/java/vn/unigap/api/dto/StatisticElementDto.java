package vn.unigap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticElementDto {
    private LocalDate date;
    @Builder.Default
    private Long numEmployer = 0L;
    @Builder.Default
    private Long numJob = 0L;
    @Builder.Default
    private Long numSeeker = 0L;
    @Builder.Default
    private Long numResume = 0L;
}
