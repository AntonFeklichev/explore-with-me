package mainservice.compilations.repository;

import mainservice.compilations.entity.Compilation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {


    @Query("SELECT c " +
           "FROM Compilation c " +
           "WHERE c.pinned = :pinned")
    Page<Compilation> getCompilationList(Boolean pinned, PageRequest pageRequest);


}
