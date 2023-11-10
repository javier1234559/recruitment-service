package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDTO;
import vn.unigap.api.dto.out.EmployerDTOResponse;
import vn.unigap.api.dto.out.EmployerDTOWithDescription;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.exception.ExistingResourceException;
import vn.unigap.api.exception.NotFoundResourceException;
import vn.unigap.api.repository.EmployerRepository;

import java.util.*;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerRepository employerRepository ;

    @Override
    public boolean checkExistEmail(String email) {
        String checkEmail = employerRepository.getExistEmail(email);
         return checkEmail != null && !checkEmail.isEmpty() ;
    }

    @Override
    public boolean checkExistProvinceId(Integer provinceId) {
        Integer checkProvince = employerRepository.getExistProvinceId(provinceId);
        return checkProvince != 0 ;
    }

    @Override
    public String createEmployer(EmployerDTO employer) {
        Employer newEmployer = new Employer();
        newEmployer.setName(employer.getName());
        newEmployer.setEmail(employer.getEmail());
        newEmployer.setProvince(employer.getProvinceId());
        newEmployer.setDescription(employer.getDescription());

        // Setting createdAt and updatedAt with the current date
        Date currentDate = new Date();
        newEmployer.setCreatedAt(currentDate);
        newEmployer.setUpdatedAt(currentDate);

        // Saving the new employer
        Employer savedEmployer = employerRepository.save(newEmployer);

        return "New employer created with ID: " + savedEmployer.getId();
    }

    @Override
    public String updateEmployer(Long id, String name, Integer province, String description) {
        Optional<Employer> existingEmployerOptional = employerRepository.findById(id);

        if (existingEmployerOptional.isPresent()) {
            Employer existingEmployer = existingEmployerOptional.get();

            if (name != null) {
                existingEmployer.setName(name);
            }

            if (!(null == province)) {
                if(checkExistProvinceId(province)){
                 throw new ExistingResourceException("Already existing this province id !!");
                }

                existingEmployer.setProvince(province);
            }

            if (description != null) {
                existingEmployer.setDescription(description);
            }

            employerRepository.save(existingEmployer);
            return "Employer with ID: " + id + " updated successfully.";
        } else {
            return "Employer with ID: " + id + " not found.";
        }
    }

    @Override
    public EmployerDTOWithDescription getEmployer(Long id) {
        Optional<Employer> employer = employerRepository.findById(id);

        if (employer.isPresent()) {
            EmployerDTOWithDescription result = new EmployerDTOWithDescription();
            Employer emp = employer.get();
            result.setId(emp.getId());
            result.setEmail(emp.getEmail());
            result.setName(emp.getName());
            result.setProvinceId(emp.getProvince());
            result.setDescription(emp.getDescription());
            // for this example, it's not clear where it comes from
            result.setProvinceName("Chua biet province name");
            return result;
        } else {
            return null;
        }
    }

    @Override
    public String deleteEmployer(Long id) {
        if (employerRepository.existsById(id)) {
            employerRepository.deleteById(id);
            return "Employer with ID: " + id + " deleted successfully.";
        } else {
            return "Employer with ID: " + id + " not found.";
        }
    }

    public List<EmployerDTOResponse> getListEmployer(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Employer> list =  employerRepository.getListEmployerByPagination(offset, pageSize);
        List<EmployerDTOResponse> listDTO = new ArrayList<>();
        for (Employer employer : list) {
            listDTO.add(mapToDTOResponse(employer));
        }
        return listDTO;
    }

    private EmployerDTOResponse mapToDTOResponse(Employer employer) {
        // Map fields from Employer to EmployerDTOResponse
        EmployerDTOResponse response = new EmployerDTOResponse();
        response.setId(employer.getId());
        response.setEmail(employer.getEmail());
        response.setName(employer.getName());
        response.setProvinceId(employer.getProvince());
        response.setProvinceName("employer.getProvinceName()");
        // Set other properties as needed
        return response;
    }
}
