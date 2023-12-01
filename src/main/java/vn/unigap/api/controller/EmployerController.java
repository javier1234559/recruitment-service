package vn.unigap.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.ApiResponse;
import vn.unigap.common.EnumStatusCode;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer")
public class EmployerController {

    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> createEmployer(@Valid @RequestBody EmployerDtoIn employerD) {
        String successMsg = "Employer created successfully !!";
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.noDataResponse(0, HttpStatus.CREATED, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<String>> updateEmployer(@Valid @PathVariable Long id, @RequestBody UpdateEmployerDtoIn updateEmployerDtoIn) {
        String successMsg = "Employer updated successfully !!";
        employerService.updateEmployer(id, updateEmployerDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<EmployerDtoOut>> getEmployerById(@PathVariable Long id) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(employerService.getEmployer(id), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<String>> deleteEmployer(@PathVariable Long id) {
        String successMsg = "Employer deleted successfully !!";
        employerService.deleteEmployer(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.noDataResponse(0, HttpStatus.OK, successMsg));
    }

    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<PageDtoOut<EmployerDtoOut>>> getListEmployer(@Valid PageDtoIn pageDtoIn) {
        String successMsg = "";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(employerService.getListEmployer(pageDtoIn), EnumStatusCode.SUCCESS, HttpStatus.OK, successMsg));
    }
}
