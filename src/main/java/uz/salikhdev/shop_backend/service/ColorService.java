package uz.salikhdev.shop_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.ColorCreateRequest;
import uz.salikhdev.shop_backend.dto.request.ColorUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.ColorResponse;
import uz.salikhdev.shop_backend.entity.Color;
import uz.salikhdev.shop_backend.exception.AlreadyExistsException;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.mapper.ColorMapper;
import uz.salikhdev.shop_backend.repository.ColorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public void create(ColorCreateRequest request) {
        if (colorRepository.existsByHexAndDeletedAtIsNull((request.hex()))) {
            throw new AlreadyExistsException("Color with name " + request.hex() + " already exists");
        }

        if(colorRepository.existsByHex(request.hex())){
            Color deletedColor = colorRepository.findByHex(request.hex())
                    .orElseThrow(() -> new NotFoundException("Deleted color not found with hex: " + request.hex()));
            deletedColor.setDeletedAt(null);
            colorRepository.save(deletedColor);
            return;
        }

        Color color = colorMapper.toEntity(request);
        colorRepository.save(color);
    }

    public void update(String id, ColorUpdateRequest request) {
        Color color = colorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Color not found with id: " + id));

        if (request.hex() != null) {
            if (!color.getHex().equals(request.hex()) && colorRepository.existsByHexAndDeletedAtIsNull(request.hex())) {
                throw new AlreadyExistsException("Color with hex " + request.hex() + " already exists");
            }
            color.setHex(request.hex());
        }

        if (request.name() != null) {
            color.setName(request.name());
        }

        colorRepository.save(color);
    }

    public void delete(String id) {
        Color color = colorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Color not found with id: " + id));

        color.setDeletedAt(LocalDateTime.now());
        colorRepository.save(color);
    }

    public ColorResponse getById(String id) {
        Color color = colorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Color not found with id: " + id));
        return colorMapper.toResponse(color);
    }

    public List<ColorResponse> getAll() {
        List<Color> colors = colorRepository.findAllByDeletedAtIsNull();
        return colorMapper.toResponse(colors);
    }

}
