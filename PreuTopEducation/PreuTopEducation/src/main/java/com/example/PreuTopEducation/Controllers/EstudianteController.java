package com.example.PreuTopEducation.Controllers;

import org.springframework.ui.Model;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller//para solicitudes html
@RequestMapping
public class EstudianteController {
    private final EstudianteService estudianteService;
    @Autowired //inyectar clase en constructor
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/estudiantes")
    public String getEstudiantes(Model model) {
        model.addAttribute("estudiantes",estudianteService.getEstudiantes());
        return "estudiantes";//retorno a html estudiantes
    }

    @GetMapping("/ingreso_estudiante")
    public String ingresoEstudiante(Model model){
        Estudiante estudiante = new Estudiante();
        model.addAttribute("estudiante");
        return "ingreso_estudiante";
    }


}
