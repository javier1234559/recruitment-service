package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;

import java.util.List;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerRepository employerRepository ;
    @Override
    public List<Employer> getAllEmployer() {
        return employerRepository.findAll();
    }
}
