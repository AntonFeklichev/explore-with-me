package mainservice.compilations.service;

import lombok.RequiredArgsConstructor;
import mainservice.compilations.dto.CompilationDto;
import mainservice.compilations.mapper.CompilationMapper;
import mainservice.compilations.repository.CompilationRepository;
import mainservice.exception.CompilationNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;


    public Page<CompilationDto> getCompilationList(Boolean pinned, PageRequest pageRequest) {


        return compilationRepository
                .getCompilationList(pinned, pageRequest)
                .map(compilationMapper::toCompilationDto);
    }


    public CompilationDto getCompilationById(Long compId) {
        return compilationRepository
                .findById(compId)
                .map(compilationMapper::toCompilationDto)
                .orElseThrow(() -> new CompilationNotFoundException("Compilation not found"));
    }


}
