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
    private Long numEmployer = 0L;
    private Long numJob = 0L;
    private Long numSeeker = 0L;
    private Long numResume = 0L;
}
