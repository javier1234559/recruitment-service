package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDto;
import vn.unigap.api.dto.in.UpdateEmployerDto;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.exception.ExistingResourceException;
import vn.unigap.api.repository.EmployerRepository;

import java.util.*;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerRepository employerRepository ;

    private boolean checkExistEmail(String email) {
        String checkEmail = employerRepository.getExistEmail(email);
         return checkEmail != null && !checkEmail.isEmpty() ;
    }

    private boolean checkExistProvinceId(Integer provinceId) {
        Integer checkProvince = employerRepository.getExistProvinceId(provinceId);
        return checkProvince != 0 ;
    }

    @Override
    public String createEmployer(EmployerDto employer) {
        String email = employer.getEmail();
        int proviceId = employer.getProvinceId();
        if(checkExistEmail(email)){
            throw new ExistingResourceException("Email is already exist");
        }
        if(checkExistProvinceId(proviceId)){
            throw new ExistingResourceException("Province id for this employer is already exist");
        }

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
    public String updateEmployer(Long id , UpdateEmployerDto updateEmployerDto) {
        Optional<Employer> existingEmployerOptional = employerRepository.findById(id);

        if (existingEmployerOptional.isPresent()) {
            Employer existingEmployer = existingEmployerOptional.get();

            if (updateEmployerDto.getName() != null) {
                existingEmployer.setName(updateEmployerDto.getName());
            }

            Integer provinceid = updateEmployerDto.getProvinceId();
            if (!(null == provinceid)) {
                // this will ensure not update the same province id with another employer
                if(!(Objects.equals(existingEmployer.getProvince(), provinceid)) && checkExistProvinceId(provinceid)){
                 throw new ExistingResourceException("Already existing this province id !!");
                }

                existingEmployer.setProvince(updateEmployerDto.getProvinceId());
            }

            if (updateEmployerDto.getDescription() != null) {
                existingEmployer.setDescription(updateEmployerDto.getDescription());
            }

            employerRepository.save(existingEmployer);
            return "Employer with ID: " + id + " updated successfully.";
        } else {
            return "Employer with ID: " + id + " not found.";
        }
    }

    @Override
    public EmployerDtoOut getEmployer(Long id) {
        Optional<Employer> employer = employerRepository.findById(id);

        if (employer.isPresent()) {
            EmployerDtoOut result = new EmployerDtoOut();
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

    @Override
    public List<Map<String, Object>> getListEmployer(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Employer> list = employerRepository.getListEmployerByPagination(offset, pageSize);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Employer employer : list) {
            resultList.add(mapToDTOResponse(employer));
        }
        return resultList;
    }

    private Map<String, Object> mapToDTOResponse(Employer employer) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", employer.getId());
        response.put("email", employer.getEmail());
        response.put("name", employer.getName());
        response.put("provinceId", employer.getProvince());
        response.put("provinceName", "DefaultProvinceName");
        // Put other properties as needed
        return response;
    }
}
