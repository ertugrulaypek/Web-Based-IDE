package edu.tum.ase.compiler.platform;

import java.io.IOException;

public class JavaPlatform implements CompilerPlatform {

    public JavaPlatform() {
    }

    @Override
    public Process createCompilationProcess(String sourceCodePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("javac", sourceCodePath);
        return processBuilder.start();
    }

}
