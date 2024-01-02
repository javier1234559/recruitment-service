package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateEmployerRequest;
import vn.unigap.api.dto.in.UpdateEmployerRequest;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.service.employer.EmployerService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/employers")
@Log4j2
@Tag(name = "Employer", description = "Quản lý employer")
@SecurityRequirement(name = "Authorization")
public class EmployerController {

    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    private static class ResponseEmployer extends CustomResponse<String> {
    }

    private static class ResponseOneEmployer extends CustomResponse<EmployerResponse> {
    }

    private static class ResponseListEmployer extends CustomResponse<PageDtoOut<EmployerResponse>> {
    }


    @Operation(
            summary = "Tạo employer mới",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<String>> create(@Valid @RequestBody CreateEmployerRequest createEmployerRequest) {
        employerService.create(createEmployerRequest);
        String successMsg = "Employer created successfully !!";


        log.info(successMsg);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.noDataResponse(0, HttpStatus.CREATED, successMsg));
    }


    @Operation(
            summary = "Sửa employer mới",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomResponse<String>> update(@PathVariable Long id, @RequestBody @Valid UpdateEmployerRequest updateEmployerRequest) {
        employerService.update(id, updateEmployerRequest);
        String successMsg = "Employer updated successfully !!";

        log.info(successMsg);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    @Operation(
            summary = "Lấy thông tin employer theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseOneEmployer.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<EmployerResponse>> getEmployerById(@PathVariable Long id) {
        String successMsg = "";

        log.info(successMsg);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(employerService.getOne(id), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

    @Operation(
            summary = "Xóa employer theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<String>> delete(@PathVariable Long id) {
        employerService.delete(id);
        String successMsg = "Employer deleted successfully !!";

        log.info(successMsg);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }


    @Operation(
            summary = "Lấy danh sách employer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseListEmployer.class
                                    )
                            )}
                    )
            }
    )
    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<PageDtoOut<EmployerResponse>>> getList(@Valid PageDtoIn pageDtoIn) {
        PageDtoOut<EmployerResponse> result = employerService.getAll(pageDtoIn);
        String successMsg = "";

        log.info(successMsg);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(result, EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }
}
