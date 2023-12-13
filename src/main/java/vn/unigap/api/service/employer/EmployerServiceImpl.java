package vn.unigap.api.service.employer;

import org.springframework.beans.factory.annotation.Autowired;
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
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;

import java.util.Date;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    private static EmployerResponse findProvinceName(vn.unigap.api.entity.Employer employer) {
        String provinceName = null;
        return EmployerResponse.from(employer, provinceName);
    }

    @Override
    public void create(CreateEmployerRequest employerDtoTn) {
        String email = employerDtoTn.getEmail();
        Optional<vn.unigap.api.entity.Employer> checkEmail = employerRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(EnumStatusCode.NOT_ACCEPTABLE, HttpStatus.CONFLICT, "Email is already exist !");
        }

        Date currentDate = new Date();
        vn.unigap.api.entity.Employer newEmployer = vn.unigap.api.entity.Employer.builder()
                .name(employerDtoTn.getName())
                .email(employerDtoTn.getEmail())
                .province(employerDtoTn.getProvinceId())
                .description(employerDtoTn.getDescription())
                .updatedAt(currentDate)
                .createdAt(currentDate).build();

        // Saving the new employer
        employerRepository.save(newEmployer);
    }

    public void update(Long id, UpdateEmployerRequest updateEmployerRequest) {
        vn.unigap.api.entity.Employer updateEmployer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );

        updateEmployer.setName(updateEmployerRequest.getName());
        updateEmployer.setProvince(updateEmployerRequest.getProvinceId());
        updateEmployer.setDescription(updateEmployerRequest.getDescription());
        employerRepository.save(updateEmployer);
    }

    @Override
    public EmployerResponse getOne(Long id) {
        vn.unigap.api.entity.Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );
        return findProvinceName(employer);
    }

    public void delete(Long id) {
        vn.unigap.api.entity.Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id " + id + " is not found!")
                );
        employerRepository.deleteById(employer.getId());
    }

    @Override
    public PageDtoOut<EmployerResponse> getAll(PageDtoIn pageDtoIn) {
        Page<vn.unigap.api.entity.Employer> employers = this.employerRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getSize(),
                        Sort.by("name").ascending()));

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getSize(),
                employers.getTotalElements(),
                employers.stream().map(EmployerServiceImpl::findProvinceName).toList());
    }

}