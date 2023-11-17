package vn.unigap.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDto;
import vn.unigap.api.dto.in.UpdateEmployerDto;
import vn.unigap.api.service.EmployerService;

@RestController
@RequestMapping("/api/v1/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService ;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployer(@Valid  @RequestBody EmployerDto employerD){
        String result = employerService.createEmployer(employerD);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEmployer(@Valid @PathVariable Long id , @RequestBody UpdateEmployerDto updateEmployerDto){
        return ResponseEntity.ok().body(employerService.updateEmployer(id, updateEmployerDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployerById(@PathVariable Long id){
        return ResponseEntity.ok().body(employerService.getEmployer(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployer(@PathVariable Long id){
        return ResponseEntity.ok().body(employerService.deleteEmployer(id));
    }

    //?page=1&size=2
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getListEmployer(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok().body(employerService.getListEmployer(page,size));
    }


}
