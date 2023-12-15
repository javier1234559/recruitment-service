package vn.unigap.api.service.seeker;

import org.springframework.stereotype.Service;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.CreateSeekerRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.in.UpdateSeekerRequest;
import vn.unigap.api.dto.out.JobListResponse;
import vn.unigap.api.dto.out.JobOneResponse;
import vn.unigap.api.dto.out.SeekerListResponse;
import vn.unigap.api.dto.out.SeekerOneResponse;

public interface SeekerService {
    void create(CreateSeekerRequest entityDTO);

    void update(Long id, UpdateSeekerRequest entityDTO);

    SeekerOneResponse getOne(Long id);

    void delete(Long id);

    PageDtoOut<SeekerListResponse> getALl(PageDtoIn pageDtoIn);

}
