package edu.tum.ase.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="project_source_file_content")
@Getter
@Setter
@NoArgsConstructor
public class SourceFileContent implements Serializable {

    @Id
    @Column(name = "file_id", unique = true, nullable = false)
    private String fileId;

    @MapsId
    @OneToOne()
    @JoinColumn(name = "file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SourceFile sourceFile;

    @Column(name = "file_content", columnDefinition = "TEXT")
    private String fileContent;

    public SourceFileContent(SourceFile sourceFile, String fileContent){
        this.sourceFile = sourceFile;
        this.fileContent = fileContent;
    }
}
