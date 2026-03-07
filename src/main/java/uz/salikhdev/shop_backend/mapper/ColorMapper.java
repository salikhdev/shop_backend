package uz.salikhdev.shop_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.salikhdev.shop_backend.dto.request.ColorCreateRequest;
import uz.salikhdev.shop_backend.dto.response.ColorResponse;
import uz.salikhdev.shop_backend.entity.Color;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ColorMapper {

    ColorResponse toResponse(Color color);

    List<ColorResponse> toResponse(List<Color> categories);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "hex", source = "hex")
    Color toEntity(ColorCreateRequest request);

}
