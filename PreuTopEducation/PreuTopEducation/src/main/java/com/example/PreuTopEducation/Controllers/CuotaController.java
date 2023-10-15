package com.example.PreuTopEducation.Controllers;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import org.springframework.ui.Model;
import com.example.PreuTopEducation.Services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public String cuotasEstudiante(@PathVariable Long id, Model model) {//obtener cuotas de un estudiante
        List<Cuota> cuotas = cuotaService.obtenerCuotasPorEstudianteId(id);
        model.addAttribute("cuotas", cuotas);
        return "ver_cuotas"; // El nombre del archivo HTML de la vista sin la barra inicial
    }

    @PostMapping("/ver_cuotas/{id}")
    public String registrarCuota(@PathVariable Long id, @RequestParam("estado") String estado) {
        Cuota cuota = cuotaService.obtenerCuotaPorId(id);
        cuota.setEstado(estado);
        LocalDate fechaPago = LocalDate.now();
        cuota.setFecha_pago(fechaPago);
        cuotaService.actualizarCuota(cuota);

        // Obtén el ID del estudiante desde la cuota
        Long estudianteId = cuota.getEstudiante().getRut();

        // Redirige a la página de cuotas del estudiante específico
        return "redirect:/ver_cuotas/" + estudianteId;
    }

    @PostMapping("/cuotas/aplicar_descuento")
    public String aplicarDescuentoATodos() {
        cuotaService.aplicarDescuentoPorPromedioExamenParaTodos();
        return "redirect:/cuotas"; // Redirige de vuelta a la página de cuotas
    }






}


