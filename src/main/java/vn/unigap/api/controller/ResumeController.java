package vn.unigap.api.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateResumeRequest;
import vn.unigap.api.dto.in.UpdateResumeRequest;
import vn.unigap.api.dto.out.ResumeListResponse;
import vn.unigap.api.dto.out.ResumeOneResponse;
import vn.unigap.api.service.resume.ResumeService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<String>> create(@Valid @RequestBody CreateResumeRequest createResumeRequest) {
        resumeService.create(createResumeRequest);
        String successMsg = "Resume created successfully !!";
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.noDataResponse(0, HttpStatus.CREATED, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomResponse<String>> update(@PathVariable Long id, @RequestBody @Valid UpdateResumeRequest updateResumeRequest) {
        String successMsg = "Resume updated successfully !!";
        resumeService.update(id, updateResumeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<ResumeOneResponse>> getOne(@PathVariable Long id) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(resumeService.getOne(id), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<String>> delete(@PathVariable Long id) {
        String successMsg = "Resume deleted successfully !!";
        resumeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse<PageDtoOut<ResumeListResponse>>> getAll(@Valid PageDtoIn pageDtoIn) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(resumeService.getALl(pageDtoIn), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

}
