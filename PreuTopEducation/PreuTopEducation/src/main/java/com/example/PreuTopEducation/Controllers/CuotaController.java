package com.example.PreuTopEducation.Controllers;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import org.springframework.ui.Model;
import com.example.PreuTopEducation.Services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class CuotaController {
    private final CuotaService cuotaService;
    @Autowired
    public CuotaController(CuotaService cuotaService){
        this.cuotaService = cuotaService;

    }

    @PostMapping("/generar_cuota")
    public String generarCuotasParaEstudiante(@RequestParam("id") Long estudianteId) {
        // Llama al método del servicio para generar las cuotas para el estudiante con el ID proporcionado
        cuotaService.generarCuotasporId(estudianteId);

        // Redirige a alguna página de éxito o vuelve al formulario, según tus necesidades
        return "redirect:/cuotas";  // Redirige a la página de cuotas después de generarlas
    }

    @GetMapping("/ver_cuotas/{id}")
    public String cuotasEstudiante(@PathVariable Long id, Model model) {
        List<Cuota> cuotas = cuotaService.obtenerCuotasPorEstudianteId(id);
        model.addAttribute("cuotas", cuotas);
        return "ver_cuotas"; // El nombre del archivo HTML de la vista sin la barra inicial
    }




}


