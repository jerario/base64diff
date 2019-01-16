package com.joaera.diff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class DocumentationController {

    //Endpoint for returning html with documentation
    @RequestMapping(value = "/doc")
    public String getDocumentation(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html");
        return "docs";
    }
}
