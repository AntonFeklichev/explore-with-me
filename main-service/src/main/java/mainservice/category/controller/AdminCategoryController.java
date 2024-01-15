package mainservice.category.controller;

import lombok.RequiredArgsConstructor;
import mainservice.category.dto.CategoryDto;
import mainservice.category.dto.NewCategoryDto;
import mainservice.category.service.AdminCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto postCategory(@Valid
                                    @RequestBody
                                    NewCategoryDto newCategoryDto) {

        return adminCategoryService.postCategory(newCategoryDto);

    }

    @DeleteMapping(path = "/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "catId")
                                               Long catId) {

        adminCategoryService.deleteCategory(catId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(path = "/{catId}")
    public CategoryDto patchCategory(@PathVariable(name = "catId")
                                     Long catId,
                                     @Valid
                                     @RequestBody
                                     CategoryDto categoryDto) {

        return adminCategoryService.patchCategory(catId, categoryDto);

    }

}
