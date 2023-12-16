package vn.unigap.api.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.CreateResumeRequest;
import vn.unigap.api.dto.in.StatisticRequest;
import vn.unigap.api.dto.out.StatisticResponse;
import vn.unigap.api.service.statistic.StatisticService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<StatisticResponse>> create(@Valid @RequestBody StatisticRequest request) {
        StatisticResponse response = statisticService.getStatisticByDate(request);
        String successMsg = "";

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.withDataResponse(response, EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }
}
