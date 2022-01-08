package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.SourceFileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceFileContentRepository extends JpaRepository<SourceFileContent, String> {
}
