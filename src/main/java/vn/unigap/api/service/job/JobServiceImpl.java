package vn.unigap.api.service.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.*;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.out.JobListResponse;
import vn.unigap.api.dto.out.JobOneResponse;
import vn.unigap.api.dto.out.JobRecommendResponse;
import vn.unigap.api.entity.*;
import vn.unigap.api.repository.*;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;
import vn.unigap.common.helper.StringParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobProvinceRepository jobProvinceRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobFieldRepository jobFieldRepository;

    private List<JobFieldDto> mapperJobFieldDto(Job job) {
        return StringParser.StringToIdList(job.getFields())
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

    private List<JobProvinceDto> mapperJobProvinceDto(Job job) {
        return StringParser.StringToIdList(job.getProvinces())
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

    private String getEmployerName(Long employerId) {
        return employerRepository.findById(employerId)
                .map(Employer::getName)
                .orElse("");
    }

    @Override
    @Transactional
    public void create(CreateJobRequest request) {
        LocalDate currentDate = LocalDate.now();

        String fieldIds = StringParser.ListIdToString(request.getFieldIds());
        String provinceIds = StringParser.ListIdToString(request.getFieldIds());

        Job newJob = Job.builder()
                .title(request.getTitle())
                .employer_id(request.getEmployerId())
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .fields(fieldIds).provinces(provinceIds)
                .salary(request.getSalary())
                .createdAt(currentDate)
                .updatedAt(currentDate)
                .build();

        // Saving the new employer
        jobRepository.save(newJob);
    }

    @Override
    @Transactional
    public void update(Long id, UpdateJobRequest request) {
        LocalDate currentDate = LocalDate.now();
        Job updateJob = jobRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job with id " + id + " is not found!")
                );

        updateJob.setTitle(request.getTitle());
        updateJob.setQuantity(request.getQuantity());
        updateJob.setDescription(request.getDescription());

        //This already check exist
        String fieldIds = StringParser.ListIdToString(request.getFieldIds());
        String provinceIds = StringParser.ListIdToString(request.getFieldIds());

        updateJob.setFields(fieldIds);
        updateJob.setProvinces(provinceIds);
        updateJob.setSalary(request.getSalary());
        updateJob.setExpiredAt(request.getExpiredAt());
        updateJob.setUpdatedAt(currentDate);
        jobRepository.save(updateJob);
    }

    @Override
    public JobOneResponse getOne(Long id) {
        Job job;
        job = jobRepository.findById(id).orElseThrow(
                () -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job with id " + id + " is not found!")
        );

        List<JobFieldDto> fieldIds = mapperJobFieldDto(job);
        List<JobProvinceDto> provinceIds = mapperJobProvinceDto(job);

        String employerName = getEmployerName(job.getEmployer_id());
        return JobOneResponse.from(job, fieldIds, provinceIds, employerName);
    }

    @Override
    public void delete(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );
        jobRepository.deleteById(job.getId());
    }

    @Override
    public PageDtoOut<JobListResponse> getALl(PageDtoIn pageDtoIn) {
        Page<Job> jobs = jobRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1,
                        pageDtoIn.getSize(),
                        Sort.by("id").ascending())
        );

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getSize(),
                jobs.getTotalElements(),
                jobs.stream().map(job -> {
                    Optional<Employer> employer = employerRepository.findById(job.getEmployer_id());
                    if (employer.isPresent()) {
                        return JobListResponse.from(job, employer.get().getName());
                    }
                    String undefineName = "";
                    return JobListResponse.from(job, undefineName);
                }).toList());
    }

    @Override
    public JobRecommendResponse getRecommendOne(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                () -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job with id " + id + " is not found!")
        );
        List<JobFieldDto> jobFields = mapperJobFieldDto(job);
        List<JobProvinceDto> jobProvinces = mapperJobProvinceDto(job);

        String employerName = getEmployerName(job.getEmployer_id());

        List<SeekerDto> seekerDtos = getSeekerDtosBySalary(job.getSalary(), StringParser.StringToIdList(job.getFields()), StringParser.StringToIdList(job.getProvinces()));
        System.out.println(seekerDtos);

        return JobRecommendResponse.from(job, jobFields, jobProvinces, employerName, seekerDtos);
    }

    private List<SeekerDto> getSeekerDtosBySalary(Integer salary, List<Integer> checkjobFields, List<Integer> checkjobProvinces) {

        System.out.println(checkjobFields);
        System.out.println(checkjobProvinces);

        return jobRepository.listRelatedSeekerBySalary(salary).stream()
                .filter(seekerDto -> {
                    Long seekerDtoId = seekerDto.getId();
                    return resumeRepository.findResumesBySeekerId(seekerDtoId)
                            .stream()
                            .map(resume -> {
                                List<Integer> fields = StringParser.StringToIdList(resume.getFields());
                                List<Integer> provinces = StringParser.StringToIdList(resume.getProvinces());

                                System.out.println(fields);
                                System.out.println(provinces);

                                // Check if all job fields are present in the resume fields
                                boolean allFieldsMatch = fields.stream().anyMatch(checkjobFields::contains);
                                // Check if any job province is present in the resume provinces
                                boolean anyProvinceMatch = provinces.stream().anyMatch(checkjobProvinces::contains);
                                return allFieldsMatch && anyProvinceMatch;
                            })
                            .anyMatch(matchResult -> matchResult);

                })
                .collect(Collectors.toList());
    }

}
