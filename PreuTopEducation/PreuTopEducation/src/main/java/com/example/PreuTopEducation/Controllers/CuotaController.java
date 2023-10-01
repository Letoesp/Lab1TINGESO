package com.example.PreuTopEducation.Controllers;

import com.example.PreuTopEducation.Entities.Estudiante;
import org.springframework.ui.Model;
import com.example.PreuTopEducation.Services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping
public class CuotaController {
    private final CuotaService cuotaService;
    @Autowired
    public CuotaController(CuotaService cuotaService){
        this.cuotaService = cuotaService;
    }

    @GetMapping("/generar_cuota")
    public String generarCuotaForm(@RequestParam("estudianteId") Long estudianteId, Model model) {

        // Redirigir a la página de cuotas
        return "redirect:/cuotas";
    }


}


