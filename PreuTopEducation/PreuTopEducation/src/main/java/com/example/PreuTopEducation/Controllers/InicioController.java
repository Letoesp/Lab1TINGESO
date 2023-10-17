package com.example.PreuTopEducation.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String mostrarPaginaInicio() {
        return "inicio"; //archivo HTML (inicio.html)
    }
}