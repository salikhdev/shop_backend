package uz.salikhdev.shop_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.salikhdev.shop_backend.dto.request.SizeCreateRequest;
import uz.salikhdev.shop_backend.dto.response.SizeResponse;
import uz.salikhdev.shop_backend.entity.Size;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SizeMapper {

    SizeResponse toResponse(Size size);

    List<SizeResponse> toResponse(List<Size> sizes);

    Size toEntity(SizeCreateRequest request);

}
