package com.example.PreuTopEducation.Controllers;
import com.example.PreuTopEducation.Services.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;


@Controller
public class ExamenController {
    private final ExamenService examenService;

    @Autowired
    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @GetMapping("/subir_notas")
    public String mostrarFormularioSubirNotas() {
        return "subir_notas";
    }

    @PostMapping("/subir_notas")
    public String subirNotas(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            examenService.cargarNotasDesdeCSV(archivo);
            // Redirige a alguna página de confirmación
            model.addAttribute("mensaje", "Notas subidas exitosamente.");
        } catch (IOException e) {
            // Maneja las excepciones según tus necesidades
            e.printStackTrace();
            model.addAttribute("mensaje", "Error al subir las notas. Por favor, revisa el archivo e inténtalo nuevamente.");
        }
        return "redirect:/subir_notas";
    }



}


