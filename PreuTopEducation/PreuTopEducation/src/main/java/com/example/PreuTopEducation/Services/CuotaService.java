package com.example.PreuTopEducation.Services;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    @Autowired
    public CuotaService(CuotaRepository cuotaRepository){
        this.cuotaRepository= cuotaRepository;
    }
    public List<Cuota> generarCuotas(Estudiante estudiante) {
        double arancelBase = 1500000.0; // 1.500.000
        int anioActual = Year.now().getValue();
        int aniosEgreso = anioActual - estudiante.getEgreso();

        // Calcular descuentos
        if ("Municipal".equals(estudiante.getTipo_colegio_proc())) {
            arancelBase = arancelBase - (arancelBase * 0.2); // 20% de descuento para colegios municipales
        } else if ("Subvencionado".equals(estudiante.getTipo_colegio_proc())) {
            arancelBase = arancelBase - (arancelBase * 0.1); // 10% de descuento para colegios subvencionados
        }

        if (aniosEgreso < 1) {
            arancelBase = arancelBase - (arancelBase * 0.15); // 15% de descuento si es menos de un año
        } else if (aniosEgreso >= 1 && aniosEgreso <= 2) {
            arancelBase = arancelBase - (arancelBase * 0.08); // 8% de descuento si es 1-2 años
        } else if (aniosEgreso >= 3 && aniosEgreso <= 4) {
            arancelBase = arancelBase - (arancelBase * 0.04); // 4% de descuento si es 3-4 años
        }

        // La cuota nunca será negativa, asegúrate de que sea al menos 0
        if (arancelBase < 0) {
            arancelBase = 0;
        }

        // Obtener la cantidad de cuotas desde el estudiante
        int cantidadCuotas = estudiante.getCantidad_cuotas();

        // Calcular el monto de cada cuota
        double montoCuota = arancelBase / cantidadCuotas;

        // Generar cuotas
        List<Cuota> cuotas = new ArrayList<>();
        for (int i = 0; i < cantidadCuotas; i++) {
            Cuota cuota = new Cuota();
            cuota.setEstudiante(estudiante);
            cuota.setMonto((int) montoCuota);
            // Ajusta otros atributos de la cuota si es necesario
            // ...
            cuotas.add(cuota);
        }

        // Guardar cuotas en la base de datos
        return cuotaRepository.saveAll(cuotas);
    }




}


