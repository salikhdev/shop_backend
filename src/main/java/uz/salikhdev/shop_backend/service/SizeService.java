package uz.salikhdev.shop_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.SizeCreateRequest;
import uz.salikhdev.shop_backend.dto.request.SizeUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.SizeResponse;
import uz.salikhdev.shop_backend.entity.Size;
import uz.salikhdev.shop_backend.exception.AlreadyExistsException;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.mapper.SizeMapper;
import uz.salikhdev.shop_backend.repository.SizeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    public void create(SizeCreateRequest request) {
        if (sizeRepository.existsByNameAndDeletedAtIsNull((request.name()))) {
            throw new AlreadyExistsException("Size with name " + request.name() + " already exists");
        }

        if (sizeRepository.existsByName(request.name())) {
            Size deletedSize = sizeRepository.findByName(request.name())
                    .orElseThrow(() -> new NotFoundException("Deleted size not found with hex: " + request.name()));
            deletedSize.setDeletedAt(null);
            sizeRepository.save(deletedSize);
            return;
        }

        Size size = sizeMapper.toEntity(request);
        sizeRepository.save(size);
    }

    public void update(String id, SizeUpdateRequest request) {
        Size size = sizeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Size not found with id: " + id));

        if (request.name() != null) {
            if (!size.getName().equals(request.name()) && sizeRepository.existsByNameAndDeletedAtIsNull(request.name())) {
                throw new AlreadyExistsException("Size with hex " + request.name() + " already exists");
            }
            size.setName(request.name());
        }
        sizeRepository.save(size);
    }

    public void delete(String id) {
        Size size = sizeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Size not found with id: " + id));

        size.setDeletedAt(LocalDateTime.now());
        sizeRepository.save(size);
    }

    public SizeResponse getById(String id) {
        Size size = sizeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Size not found with id: " + id));
        return sizeMapper.toResponse(size);
    }

    public List<SizeResponse> getAll() {
        List<Size> sizes = sizeRepository.findAllByDeletedAtIsNull();
        return sizeMapper.toResponse(sizes);
    }

}
