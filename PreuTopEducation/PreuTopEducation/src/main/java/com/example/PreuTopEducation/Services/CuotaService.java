package com.example.PreuTopEducation.Services;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.CuotaRepository;
import com.example.PreuTopEducation.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    private final EstudianteService estudianteService;

    @Autowired
    public CuotaService(CuotaRepository cuotaRepository,EstudianteService estudianteService) {
        this.cuotaRepository = cuotaRepository;
        this.estudianteService = estudianteService;
    }

    public Estudiante obtenerEstudiantePorId(Long estudianteId) {
        return estudianteService.obtenerEstudianteporId(estudianteId);
    }

    public void generarCuotasporId(Long id) {
        // Obtener el estudiante por su ID utilizando el servicio de Estudiante
        Estudiante estudiante = obtenerEstudiantePorId(id);

        double arancelTotal = 1500000.0; // Utilizar arancel fijo de 1.500.000

        // Aplicar descuento según tipo de colegio de procedencia
        if ("Municipal".equals(estudiante.getTipo_colegio_proc())) {
            arancelTotal *= 0.8; // 20% de descuento
        } else if ("Subvencionado".equals(estudiante.getTipo_colegio_proc())) {
            arancelTotal *= 0.9; // 10% de descuento
        }

        // Calcular descuento según años desde que egresó del colegio
        int añosDesdeEgreso = Year.now().getValue() - estudiante.getEgreso();
        if (añosDesdeEgreso < 1) {
            arancelTotal *= 0.85; // 15% de descuento
        } else if (añosDesdeEgreso >= 1 && añosDesdeEgreso <= 2) {
            arancelTotal *= 0.92; // 8% de descuento
        } else if (añosDesdeEgreso >= 3 && añosDesdeEgreso <= 4) {
            arancelTotal *= 0.96; // 4% de descuento
        }

        // Calcular el número de cuotas que debe pagar el estudiante
        int cantidadCuotas = estudiante.getCantidad_cuotas();
        int montoCuota = (int) Math.ceil(arancelTotal / cantidadCuotas); // Redondear hacia arriba

        // Obtener el mes y año actual
        YearMonth yearMonth = YearMonth.now();

        // Generar cuotas y guardar en la base de datos
        for (int i = 0; i < cantidadCuotas; i++) {
            LocalDate plazoInicio = yearMonth.atDay(5);
            LocalDate plazoFinal = yearMonth.atDay(10);

            // Si el plazo final es mayor que el último día del mes, ajustarlo
            if (plazoFinal.isAfter(yearMonth.atEndOfMonth())) {
                plazoFinal = yearMonth.atEndOfMonth();
            }

            Cuota cuota = new Cuota();
            cuota.setEstudiante(estudiante);
            cuota.setMonto(montoCuota);
            cuota.setEstado("Pendiente");
            cuota.setPlazo_inicio(plazoInicio);
            cuota.setPlazo_final(plazoFinal);
            cuota.setInteres(0); // No hay intereses en este ejemplo
            cuota.setDescuento(0); // No se aplica descuento directamente a las cuotas
            cuotaRepository.save(cuota);

            // Avanzar al siguiente mes para las siguientes cuotas
            yearMonth = yearMonth.plusMonths(1);
        }
    }

}














