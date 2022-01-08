package edu.tum.ase.compiler.platform;

import edu.tum.ase.compiler.model.SourceCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;


class CompilerPlatformUnitTest {

    @Mock
    JavaPlatform javaPlatform = Mockito.spy(JavaPlatform.class);

    @Mock
    Process mockCompilationProcess = Mockito.spy(Process.class);

    @Test
    void testCompilationFailsIfErrorIsNotEmpty() throws IOException {
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        String errorString = "error";
        Mockito.doReturn("dummyPath").when(javaPlatform).createFileInTempDirectory(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(errorString).when(javaPlatform).readStandardStream(Mockito.any());
        Mockito.doReturn(mockCompilationProcess).when(javaPlatform).createCompilationProcess(Mockito.anyString());
        Assertions.assertDoesNotThrow(() -> javaPlatform.compile(sourceCode));

        // then
        Assertions.assertFalse(sourceCode.isCompilable());
        Assertions.assertEquals(sourceCode.getStderr(), errorString);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testCompilationSuccessIfErrorIsEmpty(String errorString) throws IOException{
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        Mockito.doReturn("dummyPath").when(javaPlatform).createFileInTempDirectory(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(errorString).when(javaPlatform).readStandardStream(Mockito.any());
        Mockito.doReturn(mockCompilationProcess).when(javaPlatform).createCompilationProcess(Mockito.anyString());
        Assertions.assertDoesNotThrow(() -> javaPlatform.compile(sourceCode));

        // then
        Assertions.assertTrue(sourceCode.isCompilable());
        Assertions.assertEquals(sourceCode.getStderr(), errorString);
    }

}