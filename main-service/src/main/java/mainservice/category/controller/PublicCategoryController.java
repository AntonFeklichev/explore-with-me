package mainservice.category.controller;

import lombok.RequiredArgsConstructor;
import mainservice.category.dto.CategoryDto;
import mainservice.category.service.PublicCategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Validated
@RequiredArgsConstructor
public class PublicCategoryController {


    private final PublicCategoryService publicCategoryService;

    @GetMapping
    List<CategoryDto> getCategoryList(@RequestParam(name = "from", defaultValue = "0")
                                      Integer from,
                                      @RequestParam(name = "size", defaultValue = "10")
                                      Integer size) {

        PageRequest pageRequest = PageRequest.of(from, size);

        return publicCategoryService.getCategoryList(pageRequest);

    }


    @GetMapping(path = "/{catId}")
    CategoryDto getCategoryById(@PathVariable(name = "catId")
                                Long catId) {

        return publicCategoryService.getCategoryById(catId);

    }

}

