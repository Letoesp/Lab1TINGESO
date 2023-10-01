package com.example.PreuTopEducation.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/inicio")
    public String mostrarPaginaInicio() {
        return "inicio"; // Esto debe coincidir con el nombre de tu archivo HTML (inicio.html)
    }
}