package com.example.PreuTopEducation.Services;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Entities.Examen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {

    private final EstudianteService estudianteService;
    private final ExamenService examenService;
    private final CuotaService cuotaService;

    @Autowired
    public ReporteService(EstudianteService estudianteService, ExamenService examenService, CuotaService cuotaService) {
        this.estudianteService = estudianteService;
        this.examenService = examenService;
        this.cuotaService = cuotaService;
    }

    public Estudiante generarReporteEstudiante(Long rutEstudiante) {
        Estudiante estudiante = estudianteService.obtenerEstudianteporRut(rutEstudiante);

        // Calcula cantidad de exámenes rendidos
        List<Examen> examenes = examenService.obtenerExamenesPorEstudiante(rutEstudiante);
        int cantidadExamenesRendidos = examenes.size();
        estudiante.setCantidad_examenes(cantidadExamenesRendidos);

        // Calcula monto total del arancel a pagar
        List<Cuota> cuotasPagadas = cuotaService.obtenerCuotasPagadasPorEstudianteId(rutEstudiante);
        int montoTotalPagado = cuotasPagadas.stream().mapToInt(Cuota::getMonto).sum();
        estudiante.setMonto_pagado(montoTotalPagado);

        // Calcula saldo por pagar
        List<Cuota> cuotasPendientes = cuotaService.obtenerCuotasPendientesPorEstudianteId(rutEstudiante);
        int saldoPorPagar = cuotasPendientes.stream().mapToInt(Cuota::getMonto).sum();
        estudiante.setSaldo_por_pagar(saldoPorPagar);

        // Calcula número de cuotas con retraso
        long numeroCuotasRetraso = cuotasPendientes.stream().filter(cuota -> cuota.getEstado().equals("Retraso")).count();
        estudiante.setCuotas_retraso((int) numeroCuotasRetraso);

        // Calcula fecha del último pago
        LocalDate fechaUltimoPago = cuotasPagadas.stream().map(Cuota::getFecha_pago).max(LocalDate::compareTo).orElse(null);
        estudiante.setUltimo_pago(fechaUltimoPago);

        // Calcula número de cuotas pagadas
        int numeroCuotasPagadas = cuotasPagadas.size();
        estudiante.setNumero_cuotas_pagadas(numeroCuotasPagadas);

        // Calcula monto total del arancel a pagar (saldo por pagar + monto pagado)
        int arancelAPagar = estudiante.getSaldo_por_pagar() + estudiante.getMonto_pagado();
        estudiante.setArancel_a_pagar(arancelAPagar);

        return estudiante;

    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteService.getEstudiantes();
    }
}
