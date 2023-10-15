package com.example.PreuTopEducation.Controllers;

import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/reportes")
    public String verReportes(Model model) {
        List<Estudiante> estudiantes = reporteService.obtenerTodosLosEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "reportes";
    }

    @GetMapping("/ver_reporte/{rutEstudiante}")
    public String verReporte(@PathVariable Long rutEstudiante, Model model) {
        Estudiante estudiante = reporteService.generarReporteEstudiante(rutEstudiante);
        model.addAttribute("estudiante", estudiante);
        return "ver_reporte";
    }
}

