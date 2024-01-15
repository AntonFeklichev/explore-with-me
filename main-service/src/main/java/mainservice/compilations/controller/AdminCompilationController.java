package mainservice.compilations.controller;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.dto.UpdateCompilationRequest;
import mainservice.compilations.service.AdminCompilationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public CompilationDto postCompilation(@Valid
                                          @RequestBody
                                          NewCompilationDto newCompilationDto) {

        return adminCompilationService.postCompilation(newCompilationDto);

    }

    @DeleteMapping(path = "/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable(name = "compId")
                                                  Long compId) {
        adminCompilationService.deleteCompilation(compId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(name = "/{compId}")
    public CompilationDto patchCompilation(@PathVariable(name = "compId")
                                           Long compId,
                                           @Valid
                                           @RequestBody
                                           UpdateCompilationRequest updateCompilationRequest) {

        return adminCompilationService.patchCompilation(compId, updateCompilationRequest);
    }


}
