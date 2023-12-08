package mainservice.compilations.controller;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.dto.UpdateCompilationRequest;
import mainservice.compilations.service.AdminCompilationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {

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
                                           @RequestBody
                                           UpdateCompilationRequest updateCompilationRequest) {

        return adminCompilationService.patchCompilation(compId, updateCompilationRequest);
    }


}
