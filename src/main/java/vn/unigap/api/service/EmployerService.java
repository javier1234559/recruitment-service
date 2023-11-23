package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.Employer;

import java.util.List;
import java.util.Map;

public interface EmployerService {
    String createEmployer(EmployerDtoIn employer);
    String updateEmployer(Long id, UpdateEmployerDtoIn updateEmployerDtoIn);
    EmployerDtoOut getEmployer(Long id);
    String deleteEmployer(Long id);
    PageDtoOut<EmployerDtoOut> getListEmployer(PageDtoIn pageDtoIn);

}
