package vn.unigap.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.exception.ExistingResourceException;
import vn.unigap.api.exception.NotFoundResourceException;
import vn.unigap.api.service.EmployerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService ;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployer(@Valid  @RequestBody EmployerDTO employerDTO){
        System.out.println(employerDTO.toString());
        String email = employerDTO.getEmail();
        int proviceId = employerDTO.getProvinceId();
        if(employerService.checkExistEmail(email)){
           throw new ExistingResourceException("Email is already exist");
        }
       if(employerService.checkExistProvinceId(proviceId)){
            throw new ExistingResourceException("Province id for this employer is already exist");
        }
        String result = employerService.createEmployer(employerDTO);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEmployer(
            @PathVariable Long id,
            @RequestParam @Size(max = 255, message = "Name length should not be greater than 255") String name,
            @RequestParam @Min(value = 1, message = "Province ID must be at least 1") int province,
            @RequestParam(required = false) @Size(max = 255, message = "Description length should not be greater than 255") String description
    ){
        return ResponseEntity.ok().body(employerService.updateEmployer(id,name,province,description));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployer(@PathVariable Long id){
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


//    @ExceptionHandler(CustomException.class)
//    String handleCustomException(HttpServletRequest request, CustomException ex) {
//        request.setAttribute(ErrorAttributes.ERROR_ATTRIBUTE, ex);
//        return "errorView";
//    }


}
