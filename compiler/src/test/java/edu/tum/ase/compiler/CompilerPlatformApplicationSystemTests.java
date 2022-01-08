package edu.tum.ase.compiler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tum.ase.compiler.model.SourceCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CompilerPlatformApplicationSystemTests {

    private final String URL = "/compile";

    @Autowired
    private MockMvc compilerApplication;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void compileSuccessfully() throws Exception {
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        ResultActions resultActions = compilerApplication.perform(post(URL)
                .content(objectMapper.writeValueAsString(sourceCode))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName").value(fileName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(javaCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stderr").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.compilable").value(true));
    }

    @Test
    public void testCompileWithInvalidFileExtension() throws Exception {
        // given
        String javaCode = "public class App{public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
        String fileName = "App.html";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        ResultActions resultActions = compilerApplication.perform(post(URL)
                .content(objectMapper.writeValueAsString(sourceCode))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void testCompileWithInvalidSyntax() throws Exception {
        // given
        String javaCode = "public class App{public static void main(String[] args) {this is not java syntax}}";
        String fileName = "App.java";
        SourceCode sourceCode = new SourceCode();
        sourceCode.setCode(javaCode);
        sourceCode.setFileName(fileName);

        // when
        ResultActions resultActions = compilerApplication.perform(post(URL)
                .content(objectMapper.writeValueAsString(sourceCode))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName").value(fileName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(javaCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stderr").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.compilable").value(false));
    }

}
