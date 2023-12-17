package vn.unigap.api.service.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateResumeRequest;
import vn.unigap.api.dto.in.UpdateResumeRequest;
import vn.unigap.api.dto.out.ResumeListResponse;
import vn.unigap.api.dto.out.ResumeOneResponse;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.JobProvince;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.api.repository.ResumeRepository;
import vn.unigap.api.repository.SeekerRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;
import vn.unigap.common.helper.StringParser;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private SeekerRepository seekerRepository;

    @Autowired
    private JobProvinceRepository jobProvinceRepository;

    @Autowired
    private JobFieldRepository jobFieldRepository;

    private List<JobFieldDto> mapperJobFieldDto(Resume entity) {
        return StringParser.StringToIdList(entity.getFields())
                .stream()
                .map(fieldId -> {
                    String name = "";
                    Optional<JobField> field = jobFieldRepository.findById(fieldId);
                    if (field.isPresent()) {
                        name = field.get().getName();
                    }
                    return new JobFieldDto(fieldId, name);
                })
                .collect(Collectors.toList());
    }

    private List<JobProvinceDto> mapperJobProvinceDto(Resume entity) {
        return StringParser.StringToIdList(entity.getProvinces())
                .stream()
                .map(provinceId -> {
                    String name = "";
                    Optional<JobProvince> province = jobProvinceRepository.findById(provinceId);
                    if (province.isPresent()) {
                        name = province.get().getName();
                    }
                    return new JobProvinceDto(provinceId, name);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "resumesList", allEntries = true)
            }
    )
    public void create(CreateResumeRequest request) {
        LocalDate currentDate = LocalDate.now();
        Seeker seeker = seekerRepository.findById(request.getSeekerId()).orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker with id " + request.getSeekerId() + " is not found!")
        );

        String fieldIds = StringParser.ListIdToString(request.getFieldIds());
        String provinceIds = StringParser.ListIdToString(request.getFieldIds());


        Resume newResume = Resume.builder()
                .seeker_id(seeker.getId())
                .career_obj(request.getCareerObj())
                .title(request.getTitle())
                .salary(request.getSalary())
                .fields(fieldIds)
                .provinces(provinceIds)
                .createdAt(currentDate)
                .updatedAt(currentDate)
                .build();

        resumeRepository.save(newResume);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "resume", key = "#id"),
                    @CacheEvict(value = "resumesList", allEntries = true)
            }
    )
    public void update(Long id, UpdateResumeRequest request) {
        LocalDate currentDate = LocalDate.now();
        Resume updateResume = resumeRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume with id " + id + " is not found!")
                );

        updateResume.setCareer_obj(request.getCareerObj());
        updateResume.setTitle(request.getTitle());
        updateResume.setSalary(request.getSalary());

        //This already check exist
        String fieldIds = StringParser.ListIdToString(request.getFieldIds());
        String provinceIds = StringParser.ListIdToString(request.getFieldIds());

        updateResume.setFields(fieldIds);
        updateResume.setProvinces(provinceIds);
        updateResume.setUpdatedAt(currentDate);

        resumeRepository.save(updateResume);
    }

    @Override
    @Cacheable(value = "resume", key = "#id")
    public ResumeOneResponse getOne(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume with id " + id + " is not found!")
        );

        List<JobFieldDto> fieldIds = mapperJobFieldDto(resume);
        List<JobProvinceDto> provinceIds = mapperJobProvinceDto(resume);

        String seekerName = "";
        Optional<Seeker> seeker = seekerRepository.findById(resume.getSeeker_id());
        if (seeker.isPresent()) {
            seekerName = seeker.get().getName();
        }

        return ResumeOneResponse.from(resume, seekerName, fieldIds, provinceIds);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "resume", key = "#id"),
                    @CacheEvict(value = "resumesList", allEntries = true)
            }
    )
    public void delete(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume with id " + id + " is not found!")
                );
        resumeRepository.deleteById(resume.getId());
    }

    @Override
    @Cacheable(value = "resumesList", key = "#pageDtoIn.page + '-' + #pageDtoIn.size") //key will like this "2-3"
    public PageDtoOut<ResumeListResponse> getALl(PageDtoIn pageDtoIn) {
        Page<Resume> resumes = resumeRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1,
                        pageDtoIn.getSize(),
                        Sort.by("id").ascending())
        );

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getSize(),
                resumes.getTotalElements(),
                resumes.stream().map(resume -> {
                    Optional<Seeker> item = seekerRepository.findById(resume.getSeeker_id());
                    if (item.isPresent()) {
                        return ResumeListResponse.from(resume, item.get().getName());
                    }
                    String undefineName = "";
                    return ResumeListResponse.from(resume, undefineName);
                }).toList());
    }
}
