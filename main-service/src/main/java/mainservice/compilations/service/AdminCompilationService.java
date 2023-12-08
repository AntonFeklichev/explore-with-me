package mainservice.compilations.service;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.dto.UpdateCompilationRequest;
import mainservice.compilations.entity.Compilation;
import mainservice.compilations.mapper.CompilationMapper;
import mainservice.compilations.repository.CompilationRepository;
import mainservice.exception.CompilationNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {

        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);

        compilationRepository.save(compilation);

        return compilationMapper.toCompilationDto(compilation);
    }

    public void deleteCompilation(Long compId) {

        compilationRepository.deleteById(compId);
    }


    public CompilationDto patchCompilation(Long compId,
                                           UpdateCompilationRequest updateCompilationRequest) {

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException("Compilation not found"));

        compilationMapper.toCompilationFromUpdateRequest(updateCompilationRequest);

        return compilationMapper.toCompilationDto(compilation);
    }


}
