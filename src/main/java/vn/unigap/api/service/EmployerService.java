package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDTO;
import vn.unigap.api.dto.out.EmployerDTOResponse;
import vn.unigap.api.dto.out.EmployerDTOWithDescription;
import vn.unigap.api.entity.Employer;
import java.util.List;

public interface EmployerService {

    boolean checkExistEmail(String email);
    boolean checkExistProvinceId(Integer provinceId);
    String createEmployer(EmployerDTO employer);
    String updateEmployer(Long id , String name , Integer province , String description);
    EmployerDTOWithDescription getEmployer(Long id);
    String deleteEmployer(Long id);
    List<EmployerDTOResponse> getListEmployer(int page , int pageSize);

}
