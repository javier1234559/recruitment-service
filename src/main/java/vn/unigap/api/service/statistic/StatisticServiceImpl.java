package vn.unigap.api.service.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.StatisticRequest;
import vn.unigap.api.dto.out.StatisticResponse;
import vn.unigap.api.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public StatisticResponse getStatisticByDate (StatisticRequest request){
        return statisticRepository.getStatisticByDate(request.getFromDate(),request.getToDate());
    }

}
