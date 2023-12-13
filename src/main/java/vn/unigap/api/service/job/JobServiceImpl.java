package vn.unigap.api.service.job;

import org.springframework.stereotype.Service;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.out.JobResponse;

@Service
public class JobServiceImpl implements JobService {

    @Override
    public void create(CreateJobRequest employer) {

    }

    @Override
    public void update(Long id, UpdateJobRequest updateJobRequest) {

    }

    @Override
    public JobResponse get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PageDtoOut<JobResponse> getList(PageDtoIn pageDtoIn) {
        return null;
    }
}
