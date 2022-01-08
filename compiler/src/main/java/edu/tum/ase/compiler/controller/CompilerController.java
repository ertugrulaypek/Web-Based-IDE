package edu.tum.ase.compiler.controller;

import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompilerController {

    private CompilerService compilerService;

    public CompilerController(CompilerService compilerService){
        this.compilerService = compilerService;
    }

    @RequestMapping(path = "/compile", method = RequestMethod.POST)
    public SourceCode compile(@RequestBody SourceCode sourceCode){
        return compilerService.compile(sourceCode);
    }

}