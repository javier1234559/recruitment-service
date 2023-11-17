package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDto;
import vn.unigap.api.dto.in.UpdateEmployerDto;
import vn.unigap.api.dto.out.EmployerDtoOut;

import java.util.List;
import java.util.Map;

public interface EmployerService {
    String createEmployer(EmployerDto employer);
    String updateEmployer(Long id, UpdateEmployerDto updateEmployerDto);
    EmployerDtoOut getEmployer(Long id);
    String deleteEmployer(Long id);
    List<Map<String, Object>> getListEmployer(int page , int pageSize);

}
