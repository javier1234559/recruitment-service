package vn.unigap.api.service.statistic;

import vn.unigap.api.dto.in.StatisticRequest;
import vn.unigap.api.dto.out.StatisticResponse;

public interface StatisticService {

    StatisticResponse getStatisticByDate (StatisticRequest request);

}
