package uz.salikhdev.shop_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.salikhdev.shop_backend.dto.response.ProductResponse;
import uz.salikhdev.shop_backend.entity.Product;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoryMapper.class, ColorMapper.class, SizeMapper.class}
)
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "colors", source = "colors")
    @Mapping(target = "sizes", source = "sizes")
    @Mapping(target = "createdAt", source = "createdAt")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponse(List<Product> products);
}
