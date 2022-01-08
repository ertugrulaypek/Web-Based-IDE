package edu.tum.ase.compiler.platform;

import java.io.IOException;

public class CPlatform implements CompilerPlatform {

    public CPlatform() {
    }

    @Override
    public Process createCompilationProcess(String sourceCodePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("gcc", "-c", sourceCodePath);
        return processBuilder.start();
    }
}
