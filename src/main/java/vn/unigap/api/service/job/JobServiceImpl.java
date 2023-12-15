package vn.unigap.api.service.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.JobFieldDto;
import vn.unigap.api.dto.JobProvinceDto;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.out.JobListResponse;
import vn.unigap.api.dto.out.JobOneResponse;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.JobProvince;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;
import vn.unigap.common.helper.StringParser;

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

    @Override
    @Transactional
    public void create(CreateJobRequest request) {
        Date currentDate = new Date();

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
        Date currentDate = new Date();
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
        Job job = jobRepository.findById(id).orElseThrow(
                () -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job with id " + id + " is not found!")
        );

        List<JobFieldDto> fieldIds = mapperJobFieldDto(job);
        List<JobProvinceDto> provinceIds = mapperJobProvinceDto(job);

        String employerName = "";
        Optional<Employer> employer = employerRepository.findById(job.getEmployer_id());

        if(employer.isPresent()){
            employerName = employer.get().getName();
        }

        return JobOneResponse.from(job, fieldIds, provinceIds,employerName);
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
}
