package com.example.PreuTopEducation;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Entities.Examen;
import com.example.PreuTopEducation.Services.CuotaService;
import com.example.PreuTopEducation.Services.EstudianteService;
import com.example.PreuTopEducation.Services.ExamenService;
import com.example.PreuTopEducation.Services.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReporteServiceTest {

    @Mock
    private EstudianteService estudianteService;

    @Mock
    private ExamenService examenService;

    @Mock
    private CuotaService cuotaService;

    @InjectMocks
    private ReporteService reporteService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerarReporteEstudiante() {
        // Arrange
        Long estudianteRut = 204243174L;
        Estudiante leonardo = new Estudiante();
        leonardo.setRut(estudianteRut);
        leonardo.setNombres("Leonardo");

        // Configurar examenes con setters
        Examen examen1 = new Examen();
        examen1.setPuntaje(950);
        examen1.setFecha(LocalDate.of(2023, 10, 13));
        examen1.setEstudiante(leonardo);

        Examen examen2 = new Examen();
        examen2.setPuntaje(940);
        examen2.setFecha(LocalDate.of(2023, 10, 14));
        examen2.setEstudiante(leonardo);

        Examen examen3 = new Examen();
        examen3.setPuntaje(960);
        examen3.setFecha(LocalDate.of(2023, 10, 15));
        examen3.setEstudiante(leonardo);

        List<Examen> examenes = new ArrayList<>();
        examenes.add(examen1);
        examenes.add(examen2);
        examenes.add(examen3);

        // Configurar cuotas pagadas con setters
        Cuota cuota1 = new Cuota();
        cuota1.setMonto(250000);
        cuota1.setFecha_pago(LocalDate.of(2023, 9, 1));
        cuota1.setEstado("Pagado");
        cuota1.setEstudiante(leonardo);

        Cuota cuota2 = new Cuota();
        cuota2.setMonto(250000);
        cuota2.setFecha_pago(LocalDate.of(2023, 9, 15));
        cuota2.setEstado("Pagado");
        cuota2.setEstudiante(leonardo);

        Cuota cuota3 = new Cuota();
        cuota3.setMonto(250000);
        cuota3.setFecha_pago(LocalDate.of(2023, 10, 1));
        cuota3.setEstado("Pagado");
        cuota3.setEstudiante(leonardo);

        List<Cuota> cuotasPagadas = new ArrayList<>();
        cuotasPagadas.add(cuota1);
        cuotasPagadas.add(cuota2);
        cuotasPagadas.add(cuota3);

        // Configurar cuotas pendientes con setters
        Cuota cuotaPendiente = new Cuota();
        cuotaPendiente.setMonto(250000);
        cuotaPendiente.setFecha_pago(LocalDate.of(2023, 10, 15));
        cuotaPendiente.setEstado("Pendiente");
        cuotaPendiente.setEstudiante(leonardo);

        List<Cuota> cuotasPendientes = new ArrayList<>();
        cuotasPendientes.add(cuotaPendiente);

        when(examenService.obtenerExamenesPorEstudiante(estudianteRut)).thenReturn(examenes);
        when(cuotaService.obtenerCuotasPagadasPorEstudianteId(estudianteRut)).thenReturn(cuotasPagadas);
        when(cuotaService.obtenerCuotasPendientesPorEstudianteId(estudianteRut)).thenReturn(cuotasPendientes);
        when(estudianteService.obtenerEstudianteporRut(estudianteRut)).thenReturn(leonardo);

        // Act
        Estudiante resultado = reporteService.generarReporteEstudiante(estudianteRut);

        // Assert
        assertEquals(3, resultado.getCantidad_examenes());
        assertEquals(250000 * 3, resultado.getMonto_pagado());
        assertEquals(250000, resultado.getSaldo_por_pagar());
        assertEquals(0, resultado.getCuotas_retraso());
        assertEquals(LocalDate.of(2023, 10, 1), resultado.getUltimo_pago());
        assertEquals(3, resultado.getNumero_cuotas_pagadas());
        assertEquals(1000000, resultado.getArancel_a_pagar());
    }


    @Test
    public void testObtenerTodosLosEstudiantes() {
        // Arrange
        Estudiante leonardo = new Estudiante();
        leonardo.setRut(204243174L);
        leonardo.setNombres("Leonardo");

        Estudiante catalina = new Estudiante();
        catalina.setRut(123456789L);
        catalina.setNombres("Catalina");

        List<Estudiante> estudiantes = Arrays.asList(leonardo, catalina);

        // Mock del servicio para devolver la lista de estudiantes
        when(estudianteService.getEstudiantes()).thenReturn(estudiantes);

        // Act
        List<Estudiante> resultado = reporteService.obtenerTodosLosEstudiantes();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(leonardo.getRut(), resultado.get(0).getRut());
        assertEquals(leonardo.getNombres(), resultado.get(0).getNombres());
        assertEquals(catalina.getRut(), resultado.get(1).getRut());
        assertEquals(catalina.getNombres(), resultado.get(1).getNombres());
    }


}

