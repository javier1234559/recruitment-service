package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.ApiException;

import java.util.Date;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    private static EmployerDtoOut findProvinceName(Employer employer) {
        String provinceName = null;
        return EmployerDtoOut.from(employer, provinceName);
    }

    @Override
    public String createEmployer(EmployerDtoIn employerDtoTn) {
        String email = employerDtoTn.getEmail();
        Optional<Employer> checkEmail = employerRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new ApiException(EnumStatusCode.NOT_ACCEPTABLE, HttpStatus.CONFLICT, "Email is already exist !");
        }

        Date currentDate = new Date();
        Employer newEmployer = Employer.builder()
                .name(employerDtoTn.getName())
                .email(employerDtoTn.getEmail())
                .province(employerDtoTn.getProvinceId())
                .description(employerDtoTn.getDescription())
                .updatedAt(currentDate)
                .createdAt(currentDate).build();

        // Saving the new employer
        Employer savedEmployer = employerRepository.save(newEmployer);

        return "New employer created with ID: " + savedEmployer.getId();
    }

    @Override
    public String updateEmployer(Long id, UpdateEmployerDtoIn updateEmployerDtoIn) {
        Employer updateEmployer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id" + id + " is not found!")
                );

        updateEmployer.setName(updateEmployerDtoIn.getName());
        updateEmployer.setProvince(updateEmployerDtoIn.getProvinceId());
        updateEmployer.setDescription(updateEmployerDtoIn.getDescription());
        employerRepository.save(updateEmployer);
        return "Employer with ID: " + id + " updated successfully.";
    }

    @Override
    public EmployerDtoOut getEmployer(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id" + id + " is not found!")
                );
        return findProvinceName(employer);
    }

    @Override
    public String deleteEmployer(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer with id" + id + " is not found!")
                );
        employerRepository.deleteById(id);
        return "Employer with ID: " + id + " deleted successfully.";
    }

    @Override
    public PageDtoOut<EmployerDtoOut> getListEmployer(PageDtoIn pageDtoIn) {
        Page<Employer> employers = this.employerRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(),
                        Sort.by("name").ascending()));

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getPageSize(),
                employers.getTotalElements(),
                employers.stream().map(EmployerServiceImpl::findProvinceName).toList());
    }

}
