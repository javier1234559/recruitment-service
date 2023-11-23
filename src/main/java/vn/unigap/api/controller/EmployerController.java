package vn.unigap.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.service.EmployerService;

@RestController
@RequestMapping("/api/v1/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployer(@Valid @RequestBody EmployerDtoIn employerD) {
        String result = employerService.createEmployer(employerD);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEmployer(@Valid @PathVariable Long id, @RequestBody UpdateEmployerDtoIn updateEmployerDtoIn) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employerService.updateEmployer(id, updateEmployerDtoIn));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployerById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(employerService.getEmployer(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employerService.deleteEmployer(id));
    }

    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getListEmployer(@Valid PageDtoIn pageDtoIn) {
        return ResponseEntity.status(HttpStatus.FOUND).body(employerService.getListEmployer(pageDtoIn));
    }
}
