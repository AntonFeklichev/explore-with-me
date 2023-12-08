package mainservice.compilations.controller;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.service.AdminCompilationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/compilations")
@Validated
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {

        return adminCompilationService.postCompilation(newCompilationDto);

    }


}
