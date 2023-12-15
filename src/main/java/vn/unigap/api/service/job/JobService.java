package vn.unigap.api.service.job;

import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.out.JobListResponse;
import vn.unigap.api.dto.out.JobOneResponse;

public interface JobService {
    void create(CreateJobRequest entityDTO);

    void update(Long id, UpdateJobRequest entityDTO);

    JobOneResponse getOne(Long id);

    void delete(Long id);

    PageDtoOut<JobListResponse> getALl(PageDtoIn pageDtoIn);

}
