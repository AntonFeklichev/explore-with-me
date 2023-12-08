package mainservice.category.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.dto.CategoryDto;
import mainservice.category.entity.Category;
import mainservice.category.mapper.CategoryMapper;
import mainservice.category.repository.CategoryRepository;
import mainservice.exception.CategoryNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PublicCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryDto> getCategoryList(PageRequest pageRequest) {

        return categoryRepository.findAll(pageRequest)
                .map(categoryMapper::toCategoryDto)
                .getContent();
    }

    public CategoryDto getCategoryById(Long catId) {

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return categoryMapper.toCategoryDto(category);
    }

}
