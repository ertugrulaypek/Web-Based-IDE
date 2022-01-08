package edu.tum.ase.apigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping(path = "/")
    public String index(){
        return "forward:/ui/";
    }
}
