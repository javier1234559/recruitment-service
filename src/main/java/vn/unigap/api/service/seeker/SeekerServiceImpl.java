package vn.unigap.api.service.seeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.CreateSeekerRequest;
import vn.unigap.api.dto.in.UpdateSeekerRequest;
import vn.unigap.api.dto.out.SeekerListResponse;
import vn.unigap.api.dto.out.SeekerOneResponse;
import vn.unigap.api.entity.JobProvince;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.api.repository.SeekerRepository;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.CustomException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class SeekerServiceImpl implements SeekerService {

    @Autowired
    private SeekerRepository seekerRepository;

    @Autowired
    private JobProvinceRepository jobProvinceRepository;

    @Override
    public void create(CreateSeekerRequest request) {
        LocalDate currentDate = LocalDate.now();
        JobProvince jobProvince = jobProvinceRepository.findById(request.getProvinceId()).orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province with id " + request.getProvinceId() + " is not found!")
        );
        Seeker newSeeker = Seeker.builder()
                .name(request.getName())
                .address(request.getAddress())
                .birthday(request.getBirthday())
                .province(jobProvince.getId())
                .createdAt(currentDate)
                .updatedAt(currentDate).build();

        seekerRepository.save(newSeeker);
    }

    @Override
    public void update(Long id, UpdateSeekerRequest request) {
        LocalDate currentDate = LocalDate.now();
        Seeker updateSeeker = seekerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker with id " + id + " is not found!")
                );

        updateSeeker.setName(request.getName());
        updateSeeker.setAddress(request.getAddress());
        updateSeeker.setBirthday(request.getBirthday());
        JobProvince jobProvince = jobProvinceRepository.findById(request.getProvinceId()).orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province with id " + request.getProvinceId() + " is not found!")
        );
        updateSeeker.setProvince(jobProvince.getId());
        updateSeeker.setUpdatedAt(currentDate);

        seekerRepository.save(updateSeeker);
    }

    @Override
    public SeekerOneResponse getOne(Long id) {
        Seeker seeker = seekerRepository.findById(id).orElseThrow(
                () -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker with id " + id + " is not found!")
        );

        Optional<JobProvince> jobProvince = jobProvinceRepository.findById(seeker.getProvince());
        String provinceName = "";
        if (jobProvince.isPresent()) {
            provinceName = jobProvince.get().getName();
        }

        return SeekerOneResponse.from(seeker, provinceName);
    }

    @Override
    public void delete(Long id) {
        Seeker seeker = seekerRepository.findById(id)
                .orElseThrow(() -> new CustomException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker with id " + id + " is not found!")
                );
        seekerRepository.deleteById(seeker.getId());
    }

    @Override
    public PageDtoOut<SeekerListResponse> getALl(PageDtoIn pageDtoIn) {
        Page<Seeker> seekers = seekerRepository.findAll(
                PageRequest.of(pageDtoIn.getPage() - 1,
                        pageDtoIn.getSize(),
                        Sort.by("id").ascending())
        );

        return PageDtoOut.from(pageDtoIn.getPage(),
                pageDtoIn.getSize(),
                seekers.getTotalElements(),
                seekers.stream().map(seeker -> {
                    Optional<JobProvince> item = jobProvinceRepository.findById(seeker.getProvince());
                    if (item.isPresent()) {
                        return SeekerListResponse.from(seeker, item.get().getName());
                    }
                    String undefineName = "";
                    return SeekerListResponse.from(seeker, undefineName);
                }).toList());
    }
}
