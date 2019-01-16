package com.joaera.diff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class DocumentationController {

    @RequestMapping(value = "/doc")
    public String getDocumentation(HttpServletResponse response){
        response.setHeader("Content-Type","text/html");
        return "docs";
    }
}
