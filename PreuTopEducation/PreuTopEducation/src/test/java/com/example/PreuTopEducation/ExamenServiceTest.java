package com.example.PreuTopEducation;

import com.example.PreuTopEducation.Services.ExamenService;
import com.example.PreuTopEducation.Entities.Examen;
import com.example.PreuTopEducation.Repositories.ExamenRepository;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Services.EstudianteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExamenServiceTest {

    @Mock
    private ExamenRepository examenRepository;

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private ExamenService examenService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Configurar comportamiento del mock si es necesario
    }

    @Test
    public void testObtenerExamenesPorEstudiante() {
        // Arrange
        Long estudianteRut = 204243174L;
        Estudiante leonardo = new Estudiante();
        leonardo.setRut(estudianteRut);
        leonardo.setNombres("Leonardo");

        Examen examen1 = new Examen();
        examen1.setPuntaje(950);
        examen1.setFecha(LocalDate.of(2023, 10, 13));
        examen1.setEstudiante(leonardo);

        Examen examen2 = new Examen();
        examen2.setPuntaje(940);
        examen2.setFecha(LocalDate.of(2023, 10, 13));
        examen2.setEstudiante(leonardo);

        // Simula el comportamiento del repositorio al buscar exámenes por el rut del estudiante
        when(examenRepository.findExamenByEstudiante_Rut(estudianteRut)).thenReturn(Arrays.asList(examen1, examen2));

        // Simula el comportamiento del servicio de estudiantes al obtener un estudiante por su rut
        when(estudianteService.obtenerEstudianteporRut(estudianteRut)).thenReturn(leonardo);

        // Act
        List<Examen> examenes = examenService.obtenerExamenesPorEstudiante(estudianteRut);

        // Assert
        // Verifica que se obtengan los exámenes con los puntajes y fechas esperados
        assertEquals(2, examenes.size());
        assertEquals(950, examenes.get(0).getPuntaje());
        assertEquals(940, examenes.get(1).getPuntaje());
        assertEquals(LocalDate.of(2023, 10, 13), examenes.get(0).getFecha());
        assertEquals(LocalDate.of(2023, 10, 13), examenes.get(1).getFecha());
    }

    @Test
    public void testCalcularYActualizarPromedioParaTodosLosEstudiantes() {
        // Arrange
        Long estudianteRut = 204243174L;
        Estudiante leonardo = new Estudiante();
        leonardo.setRut(estudianteRut);

        Examen examen1 = new Examen();
        examen1.setPuntaje(950);
        examen1.setEstudiante(leonardo); // Asociar el examen con el estudiante

        Examen examen2 = new Examen();
        examen2.setPuntaje(940);
        examen2.setEstudiante(leonardo); // Asociar el examen con el estudiante

        // Simular el comportamiento del método obtenerExamenesPorEstudiante
        when(examenRepository.findExamenByEstudiante_Rut(estudianteRut)).thenReturn(Arrays.asList(examen1, examen2));

        // Simular el comportamiento del método getEstudiantes del estudianteService
        when(estudianteService.getEstudiantes()).thenReturn(List.of(leonardo));

        // Act
        examenService.calcularYActualizarPromedioParaTodosLosEstudiantes();

        // Assert
        // Verificar que el promedio se ha calculado correctamente (promedio = (950 + 940) / 2 = 945)
        assertEquals(945, leonardo.getPromedio_examen());
        // Verificar que el método actualizarEstudiante del estudianteService se llamó exactamente una vez
        verify(estudianteService, times(1)).actualizarEstudiante(any(Estudiante.class));



    }


}

