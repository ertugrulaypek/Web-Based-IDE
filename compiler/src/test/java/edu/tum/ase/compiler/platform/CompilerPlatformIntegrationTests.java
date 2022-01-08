package edu.tum.ase.compiler.platform;

import edu.tum.ase.compiler.model.SourceCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompilerPlatformIntegrationTests {

    JavaPlatform javaPlatform = new JavaPlatform();

    @Test
    public void testSuccessfulJavaCompilation() {
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        Assertions.assertDoesNotThrow(() -> javaPlatform.compile(sourceCode));

        //then
        Assertions.assertTrue(sourceCode.isCompilable());
        Assertions.assertTrue(sourceCode.getStderr().isEmpty());
    }

    @Test
    public void testCompileWhenClassAndFilenameMismatch() {
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "another.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        Assertions.assertDoesNotThrow(() -> javaPlatform.compile(sourceCode));

        //then
        Assertions.assertFalse(sourceCode.isCompilable());
        Assertions.assertTrue(!sourceCode.getStderr().isEmpty());
    }

    @Test
    public void testCompileWhenInvalidSyntax() {
        // given
        String javaCode = "public class App{public static void main(String[] args) {this can not be a java code}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        Assertions.assertDoesNotThrow(() -> javaPlatform.compile(sourceCode));

        //then
        Assertions.assertFalse(sourceCode.isCompilable());
        Assertions.assertTrue(!sourceCode.getStderr().isEmpty());
    }

}
