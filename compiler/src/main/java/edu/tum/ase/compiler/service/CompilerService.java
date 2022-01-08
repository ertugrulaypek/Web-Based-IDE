package edu.tum.ase.compiler.service;

import edu.tum.ase.compiler.factory.CompilerFactory;
import edu.tum.ase.compiler.model.SourceCode;
import org.springframework.stereotype.Service;
import edu.tum.ase.compiler.platform.CompilerPlatform;

import java.io.IOException;

@Service
public class CompilerService {

    public SourceCode compile(SourceCode sourceCode){
        CompilerPlatform compilerPlatform = CompilerFactory.createCompiler(sourceCode.getFileName());
        try {
            return compilerPlatform.compile(sourceCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
