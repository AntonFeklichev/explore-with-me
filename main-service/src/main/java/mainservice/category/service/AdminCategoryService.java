package mainservice.category.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.dto.CategoryDto;
import mainservice.category.dto.NewCategoryDto;
import mainservice.category.entity.Category;
import mainservice.category.mapper.CategoryMapper;
import mainservice.category.mapper.NewCategoryDtoToCategoryMapper;
import mainservice.category.repository.CategoryRepository;
import mainservice.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final NewCategoryDtoToCategoryMapper newCategoryDtoToCategoryMapper;
    private final CategoryMapper categoryMapper;

    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {

        Category category = newCategoryDtoToCategoryMapper.toCategory(newCategoryDto);

        categoryRepository.save(category);

        return categoryMapper.toCategoryDto(category);

    }

    public void deleteCategory(Long catId) {

        categoryRepository.deleteById(catId);
    }

    public CategoryDto patchCategory(Long catId, CategoryDto categoryDto) {


        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        categoryMapper.toCategory(categoryDto, category);

        return categoryMapper.toCategoryDto(category);

    }


}
