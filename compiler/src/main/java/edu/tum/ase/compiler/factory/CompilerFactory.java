package edu.tum.ase.compiler.factory;

import edu.tum.ase.compiler.exceptions.UnsupportedFileTypeException;
import edu.tum.ase.compiler.platform.CompilerPlatform;
import edu.tum.ase.compiler.platform.CPlatform;
import edu.tum.ase.compiler.platform.JavaPlatform;

public class CompilerFactory {

    private static final String cFileExtension = ".c";
    private static final String javaFileExtension = ".java";

    public static CompilerPlatform createCompiler(String sourceFileName){
        if(sourceFileName.endsWith(cFileExtension))
            return new CPlatform();
        else if(sourceFileName.endsWith(javaFileExtension))
            return new JavaPlatform();
        else
            throw new UnsupportedFileTypeException();
    }
}
