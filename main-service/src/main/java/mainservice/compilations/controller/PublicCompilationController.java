package mainservice.compilations.controller;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.service.PublicCompilationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Validated
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilationList(@RequestParam(name = "pinned", defaultValue = "false")
                                                   Boolean pinned,
                                                   @RequestParam(name = "from", defaultValue = "0")
                                                   Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10")
                                                   Integer size) {

        PageRequest pageRequest = PageRequest.of(from, size);

        return publicCompilationService
                .getCompilationList(pinned, pageRequest)
                .getContent();

    }

    @GetMapping(path = "/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilationById(@PathVariable(name = "compId")
                                             Long compId) {
        return publicCompilationService.getCompilationById(compId);

    }


}
