package vn.unigap.api.service.employer;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateEmployerRequest;
import vn.unigap.api.dto.in.UpdateEmployerRequest;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.JobProvince;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobProvinceRepository jobProvinceRepository;

    private EmployerResponse findProvinceName(Employer employer) {
        Integer id = employer.getProvince();
        Optional<JobProvince> jobProvince = jobProvinceRepository.findById(id);
        return EmployerResponse.from(employer, jobProvince.get().getName());
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "employersList", allEntries = true)
            }
    )
    public void create(CreateEmployerRequest employerDtoTn) {
        String email = employerDtoTn.getEmail();
        Optional<Employer> checkEmail = employerRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(EnumStatusCode.NOT_ACCEPTABLE, HttpStatus.CONFLICT, "Email is already exist !");
        }

        LocalDate currentDate = LocalDate.now();
        Employer newEmployer = Employer.builder()
                .name(employerDtoTn.getName())
                .email(employerDtoTn.getEmail())
                .province(employerDtoTn.getProvinceId())
                .description(employerDtoTn.getDescription())
                .updatedAt(currentDate)
                .createdAt(currentDate).build();

        // Saving the new employer
        employerRepository.save(newEmployer);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "employer", key = "#id"),
                    @CacheEvict(value = "employersList", allEntries = true)
            }
    )
    @Override
    public void update(Long id, UpdateEmployerRequest updateEmployerRequest) {
        Employer updateEmployer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );

        updateEmployer.setName(updateEmployerRequest.getName());
        updateEmployer.setProvince(updateEmployerRequest.getProvinceId());
        updateEmployer.setDescription(updateEmployerRequest.getDescription());
        employerRepository.save(updateEmployer);
    }

    @Override
    @Cacheable(value = "employer", key = "#id")
    public EmployerResponse getOne(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );
        return findProvinceName(employer);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "employer", key = "#id"),
                    @CacheEvict(value = "employersList", allEntries = true)
            }
    )
    public void delete(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );
        employerRepository.deleteById(employer.getId());
    }

    @Override
    @Cacheable(value = "employersList", key = "#pageDtoIn.page + '-' + #pageDtoIn.size") //key will like this "2-3"
    public PageDtoOut<EmployerResponse> getAll(PageDtoIn pageDtoIn) {
        Page<Employer> employers = this.employerRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getSize(),
                        Sort.by("name").ascending()));

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getSize(),
                employers.getTotalElements(),
                employers.stream().map(this::findProvinceName).toList());
    }

}
