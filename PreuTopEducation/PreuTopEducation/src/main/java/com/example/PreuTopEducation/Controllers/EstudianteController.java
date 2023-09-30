package com.example.PreuTopEducation.Controllers;

import org.springframework.ui.Model;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/estudiantes")//Leer todos los estudiantes en pantalla
    public String getEstudiantes(Model model) {
        model.addAttribute("estudiantes",estudianteService.getEstudiantes());
        return "estudiantes";//retorno a html estudiantes
    }

    @GetMapping("/ingreso_estudiante")//Recibe el objeto estudiante al formulario para trabajar con él
    public String registroEstudiante(Model model) {
        Estudiante estudiante = new Estudiante();
        model.addAttribute("estudiante", estudiante); // Agrega el objeto estudiante al modelo con el nombre "estudiante"
        return "ingreso_estudiante";
    }
    @PostMapping("/ingreso_estudiante")//Post para ingresar datos del estudiante al sistema.
    public String registrarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante){
        estudianteService.registrarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }




}
