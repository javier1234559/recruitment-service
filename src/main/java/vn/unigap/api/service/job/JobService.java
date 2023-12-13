package vn.unigap.api.service.job;

import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.out.JobResponse;

public interface JobService {
    void create(CreateJobRequest employer);
    void update(Long id, UpdateJobRequest updateJobRequest);
    JobResponse get(Long id);
    void delete(Long id);
    PageDtoOut<JobResponse> getList(PageDtoIn pageDtoIn);

}
