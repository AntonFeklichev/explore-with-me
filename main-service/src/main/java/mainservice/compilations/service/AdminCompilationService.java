package mainservice.compilations.service;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.dto.NewCompilationDto;
import mainservice.compilations.entity.Compilation;
import mainservice.compilations.mapper.CompilationMapper;
import mainservice.compilations.repository.CompilationRepository;
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
}
