package mainservice.category.service;

import lombok.RequiredArgsConstructor;
import mainservice.category.dto.CategoryDto;
import mainservice.category.dto.NewCategoryDto;
import mainservice.category.entity.Category;
import mainservice.category.mapper.CategoryMapper;
import mainservice.category.mapper.NewCategoryDtoToCategoryMapper;
import mainservice.category.repository.CategoryRepository;
import mainservice.event.repository.EventRepository;
import mainservice.exception.CategoryExistsException;
import mainservice.exception.CategoryNotFoundException;
import mainservice.exception.EventPublishedExceptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final NewCategoryDtoToCategoryMapper newCategoryDtoToCategoryMapper;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;

    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {

        Category category = newCategoryDtoToCategoryMapper.toCategory(newCategoryDto);

        Long count = categoryRepository.countCategoryByName(newCategoryDto.getName());
        if(count != 0) {
            throw new CategoryExistsException("Name of category is already taken"); //TODO уменьшить количество обращений к базе
        }

        categoryRepository.save(category);

        return categoryMapper.toCategoryDto(category);

    }

    public void deleteCategory(Long catId) {

        Long count = eventRepository.countEventByCategory(catId);
        if (count != 0) {
            throw new EventPublishedExceptions("Category have attached event");
        }
        categoryRepository.deleteById(catId);
    }

    public CategoryDto patchCategory(Long catId, CategoryDto categoryDto) {


        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Long count = categoryRepository.countCategoryByName(categoryDto.getName());
        if(count != 0) {
            throw new CategoryExistsException("Name of category is already taken"); //TODO уменьшить количество обращений к базе
        }

        categoryMapper.toCategory(categoryDto, category);

        return categoryMapper.toCategoryDto(category);

    }


}
