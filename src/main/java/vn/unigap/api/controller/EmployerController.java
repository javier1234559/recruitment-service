package vn.unigap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;
import vn.unigap.api.dto.out.EmployerDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService ;

    @GetMapping("/getall")
    public ResponseEntity<List<Employer>> createEmployer(){
        return new ResponseEntity<List<Employer>>(employerService.getAllEmployer(), HttpStatus.FOUND) ;

    }



}
