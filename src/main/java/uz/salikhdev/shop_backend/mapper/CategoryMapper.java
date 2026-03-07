package uz.salikhdev.shop_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.salikhdev.shop_backend.dto.request.CategoryCreateRequest;
import uz.salikhdev.shop_backend.dto.response.CategoryResponse;
import uz.salikhdev.shop_backend.entity.Category;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "icon", source = "icon")
    @Mapping(target = "createdAt", source = "createdAt")
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponse(List<Category> categories);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "icon", source = "icon")
    Category toEntity(CategoryCreateRequest request);

}
