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

    @GetMapping("/registro-estudiantes")
    public String getEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteService.getEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "registro-estudiantes";
    }




}
