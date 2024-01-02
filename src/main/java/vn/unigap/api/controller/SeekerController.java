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
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateSeekerRequest;
import vn.unigap.api.dto.in.UpdateSeekerRequest;
import vn.unigap.api.dto.out.SeekerListResponse;
import vn.unigap.api.dto.out.SeekerOneResponse;
import vn.unigap.api.service.seeker.SeekerService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/seekers")
@Tag(name = "Seeker", description = "Quản lý người tìm việc")
@SecurityRequirement(name = "Authorization")
public class SeekerController {

    private final SeekerService seekerService;

    @Autowired
    public SeekerController(SeekerService seekerService) {
        this.seekerService = seekerService;
    }


    private static class ResponseSeeker extends CustomResponse<String> {
    }

    private static class ResponseOneSeeker extends CustomResponse<SeekerOneResponse> {
    }

    private static class ResponseListSeeker extends CustomResponse<PageDtoOut<SeekerListResponse>> {
    }

    @Operation(
            summary = "Tạo seeker mới",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<String>> create(@Valid @RequestBody CreateSeekerRequest createSeekerRequest) {
        seekerService.create(createSeekerRequest);
        String successMsg = "Seeker created successfully !!";
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.noDataResponse(0, HttpStatus.CREATED, successMsg));
    }


    @Operation(
            summary = "Sửa seeker mới",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomResponse<String>> update(@PathVariable Long id, @RequestBody @Valid UpdateSeekerRequest updateSeekerRequest) {
        String successMsg = "Seeker updated successfully !!";
        seekerService.update(id, updateSeekerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    @Operation(
            summary = "Lấy thông tin seeker theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseOneSeeker.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<SeekerOneResponse>> getOne(@PathVariable Long id) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(seekerService.getOne(id), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

    @Operation(
            summary = "Xóa seeker theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<String>> delete(@PathVariable Long id) {
        String successMsg = "Seeker deleted successfully !!";
        seekerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }


    @Operation(
            summary = "Lấy danh sách seeker",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseListSeeker.class
                                    )
                            )}
                    )
            }
    )
    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<PageDtoOut<SeekerListResponse>>> getAll(@Valid PageDtoIn pageDtoIn) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(seekerService.getALl(pageDtoIn), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

}
