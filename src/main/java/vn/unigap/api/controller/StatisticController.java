package vn.unigap.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.StatisticRequest;
import vn.unigap.api.dto.out.StatisticResponse;
import vn.unigap.api.service.statistic.StatisticService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistic", description = "Thống kê")
@SecurityRequirement(name = "Authorization")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    private static class ResponseStatistic extends CustomResponse<StatisticResponse> {
    }


    @Operation(
            summary = "Lấy thông tin thống kê",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseStatistic.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<StatisticResponse>> create(@Valid @RequestBody StatisticRequest request) {
        StatisticResponse response = statisticService.getStatisticByDate(request);
        String successMsg = "";

        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(response, EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }
}
