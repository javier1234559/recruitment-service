package vn.unigap.api.service.resume;

import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateResumeRequest;
import vn.unigap.api.dto.in.UpdateResumeRequest;
import vn.unigap.api.dto.out.ResumeListResponse;
import vn.unigap.api.dto.out.ResumeOneResponse;

public interface ResumeService {
    void create(CreateResumeRequest entityDTO);

    void update(Long id, UpdateResumeRequest entityDTO);

    ResumeOneResponse getOne(Long id);

    void delete(Long id);

    PageDtoOut<ResumeListResponse> getALl(PageDtoIn pageDtoIn);

}
