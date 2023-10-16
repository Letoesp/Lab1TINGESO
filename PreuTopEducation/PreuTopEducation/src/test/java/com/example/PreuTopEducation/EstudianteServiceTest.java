package com.example.PreuTopEducation;

import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.EstudianteRepository;
import com.example.PreuTopEducation.Services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEstudiantes() {
        // Arrange
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombres("Leonardo");
        estudiante1.setApellidos("Espinoza");
        estudiante1.setRut(204243174L);
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombres("Catalina");
        estudiante2.setApellidos("Jofré");
        estudiante2.setRut(203189435L);
        List<Estudiante> estudiantes = Arrays.asList(estudiante1, estudiante2);

        // Configura el comportamiento esperado del repositorio
        when(estudianteRepository.findAll()).thenReturn(estudiantes);

        // Act
        List<Estudiante> estudiantesObtenidos = estudianteService.getEstudiantes();

        // Assert
        assertEquals(2, estudiantesObtenidos.size());
        assertEquals("Leonardo", estudiantesObtenidos.get(0).getNombres());
        assertEquals("Espinoza", estudiantesObtenidos.get(0).getApellidos());
        assertEquals(204243174L, estudiantesObtenidos.get(0).getRut());
        assertEquals("Catalina", estudiantesObtenidos.get(1).getNombres());
        assertEquals("Jofré", estudiantesObtenidos.get(1).getApellidos());
        assertEquals(203189435L, estudiantesObtenidos.get(1).getRut());
    }

    @Test
    public void testRegistrarEstudiante() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setNombres("Leonardo");
        estudiante.setApellidos("Espinoza");
        estudiante.setRut(204243174L);

        // Configura el comportamiento esperado del repositorio
        when(estudianteRepository.save(estudiante)).thenReturn(estudiante);

        // Act
        Estudiante estudianteRegistrado = estudianteService.registrarEstudiante(estudiante);

        // Assert
        // Verifica que el método save del repositorio se llamó con el estudiante esperado
        verify(estudianteRepository).save(estudiante);
    }

    @Test
    public void testActualizarEstudiante() {
        // Arrange
        Estudiante estudianteOriginal = new Estudiante();
        estudianteOriginal.setNombres("Leonardo");
        estudianteOriginal.setApellidos("Espinoza");
        estudianteOriginal.setRut(204243174L);
        Estudiante estudianteActualizado = new Estudiante();
        estudianteActualizado.setNombres("Leo");
        estudianteActualizado.setApellidos("Espinoza");
        estudianteActualizado.setRut(204243174L);

        // Configura el comportamiento esperado del repositorio
        when(estudianteRepository.save(estudianteActualizado)).thenReturn(estudianteActualizado);

        // Act
        Estudiante estudianteActualizadoResult = estudianteService.actualizarEstudiante(estudianteActualizado);

        // Assert
        // Verifica que el método save del repositorio se llamó con el estudiante actualizado
        verify(estudianteRepository).save(estudianteActualizado);
    }

    @Test
    public void testObtenerEstudiantePorRut() {
        // Arrange
        Long rutBuscado = 204243174L;
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setNombres("Leonardo");
        estudianteMock.setApellidos("Espinoza");
        estudianteMock.setRut(rutBuscado);
        when(estudianteRepository.findById(rutBuscado)).thenReturn(Optional.of(estudianteMock));

        // Act
        Estudiante estudianteObtenido = estudianteService.obtenerEstudianteporRut(rutBuscado);

        // Assert
        // Verifica que el estudiante retornado es el mismo que se encuentra en el repositorio
        assertEquals(estudianteMock, estudianteObtenido);
    }

    @Test
    public void testEliminarEstudiante() {
        // Arrange
        Long rutAEliminar = 204243174L;

        // Act
        estudianteService.eliminarEstudiante(rutAEliminar);

        // Assert
        // Verifica que el método deleteById del repositorio se llamó con el rut del estudiante a eliminar
        verify(estudianteRepository).deleteById(rutAEliminar);
    }

    @Test
    public void testGetEstudianteporTipopago() {
        // Arrange
        Estudiante leonardo = new Estudiante();
        leonardo.setNombres("Leonardo");
        leonardo.setTipopago("Cuotas");

        Estudiante catalina = new Estudiante();
        catalina.setNombres("Catalina");
        catalina.setTipopago("Contado");

        // Simula el comportamiento del repositorio al buscar estudiantes por tipo de pago
        when(estudianteRepository.findEstudiantesByTipopago("Cuotas")).thenReturn(Arrays.asList(leonardo));

        // Act
        List<Estudiante> estudiantes = estudianteService.getEstudianteporTipopago("Cuotas");

        // Assert
        // Verifica que solo se obtengan los estudiantes que pagan por Cuotas
        assertEquals(1, estudiantes.size());
        assertEquals("Leonardo", estudiantes.get(0).getNombres());
    }








}

