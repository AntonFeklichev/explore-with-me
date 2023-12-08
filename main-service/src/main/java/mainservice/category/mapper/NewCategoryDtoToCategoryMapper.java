package mainservice.category.mapper;

import mainservice.category.dto.NewCategoryDto;
import mainservice.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewCategoryDtoToCategoryMapper {

    Category toCategory(NewCategoryDto newCategoryDto);

}
