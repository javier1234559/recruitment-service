package vn.unigap.api.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.service.job.JobService;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<String>> createJob(@Valid @RequestBody CreateJobRequest JobD) {
        String successMsg = "Job created successfully !!";
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponse.noDataResponse(0, HttpStatus.CREATED, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomResponse<String>> updateJob(@PathVariable Long id, @RequestBody @Valid UpdateJobRequest updateJobRequest) {
        String successMsg = "Job updated successfully !!";
        jobService.update(id, updateJobRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.withDataResponse(jobService.get(id), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<String>> deleteJob(@PathVariable Long id) {
        String successMsg = "Job deleted successfully !!";
        jobService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getListJob(@Valid PageDtoIn pageDtoIn) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body("CHECKAFAF");
    }

}