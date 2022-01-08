package edu.tum.ase.compiler.platform;

import edu.tum.ase.compiler.model.SourceCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public interface CompilerPlatform {

    Process createCompilationProcess(String sourceCodePath) throws IOException;

    default SourceCode compile(SourceCode sourceCode) throws IOException {
        String sourceCodePath = createFileInTempDirectory(sourceCode.getFileName(), sourceCode.getCode());
        Process compilationProcess = createCompilationProcess(sourceCodePath);
        sourceCode.setStdout(readStandardStream(compilationProcess.getInputStream()));
        sourceCode.setStderr(readStandardStream(compilationProcess.getErrorStream()));
        sourceCode.setCompilable(null == sourceCode.getStderr() || sourceCode.getStderr().isEmpty());
        return sourceCode;
    }

    default String readStandardStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while( (line = reader.readLine()) != null){
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    default String createFileInTempDirectory(String fileName, String content) throws IOException{
        Path directoryPath = Files.createTempDirectory("temp");
        String absoluteFilePath = directoryPath.toString() + File.separator + fileName;
        File sourceFile = new File(absoluteFilePath);
        FileWriter fileWriter = new FileWriter(sourceFile);
        fileWriter.write(content);
        fileWriter.close();
        directoryPath.toFile().deleteOnExit();
        return absoluteFilePath;
    }
}
