package edu.tum.ase.compiler.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SourceCode {
    private String code;
    private String fileName;
    private String stdout;
    private String stderr;
    private boolean compilable = false;
}
