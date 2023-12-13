package vn.unigap.api.service.employer;

import vn.unigap.api.dto.in.CreateEmployerRequest;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerRequest;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.dto.PageDtoOut;

public interface EmployerService {
    void create(CreateEmployerRequest employer);
    void update(Long id, UpdateEmployerRequest updateEmployerRequest);
    EmployerResponse getOne(Long id);
    void delete(Long id);
    PageDtoOut<EmployerResponse> getAll(PageDtoIn pageDtoIn);

}
