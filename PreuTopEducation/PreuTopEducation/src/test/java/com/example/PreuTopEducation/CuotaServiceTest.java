package com.example.PreuTopEducation;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.CuotaRepository;

import com.example.PreuTopEducation.Services.CuotaService;
import com.example.PreuTopEducation.Services.EstudianteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CuotaServiceTest {

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private CuotaService cuotaService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObtenerEstudiantePorId() {
        Long estudianteId = 204243174L;
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setRut(estudianteId);
        estudianteMock.setNombres("Leonardo Iván");
        estudianteMock.setApellidos("Espinoza Ortiz");
        // comportamiento esperado del servicio de estudiantes
        when(estudianteService.obtenerEstudianteporRut(estudianteId)).thenReturn(estudianteMock);
        Estudiante estudianteObtenido = cuotaService.obtenerEstudiantePorId(estudianteId);

        // Verifica que el estudiante obtenido sea el esperado
        assertEquals(estudianteId, estudianteObtenido.getRut());
        assertEquals("Leonardo Iván", estudianteObtenido.getNombres());
        assertEquals("Espinoza Ortiz", estudianteObtenido.getApellidos());
        // Verifica que el método del servicio de estudiantes se llamó exactamente una vez con el ID esperado
        verify(estudianteService, times(1)).obtenerEstudianteporRut(estudianteId);
    }


    @Test
    public void testGenerarCuotasporId() {
        // Arrange
        Long estudianteId = 204243174L;
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setRut(estudianteId);
        estudianteMock.setNombres("Leonardo Iván");
        estudianteMock.setApellidos("Espinoza Ortiz");
        estudianteMock.setTipo_colegio_proc("Municipal");
        estudianteMock.setEgreso(2019);
        estudianteMock.setCantidad_cuotas(3);
        // Configura el comportamiento esperado del servicio de estudiantes
        when(estudianteService.obtenerEstudianteporRut(estudianteId)).thenReturn(estudianteMock);
        // Act
        cuotaService.generarCuotasporId(estudianteId);
        // Assert
        // Verifica que el método del servicio de estudiantes se llamó exactamente una vez con el ID esperado
        verify(estudianteService, times(1)).obtenerEstudianteporRut(estudianteId);
        // Verifica que se hayan generado 3 cuotas para el estudiante
        verify(cuotaRepository, times(3)).save(any(Cuota.class));
    }



    @Test
    public void testObtenerCuotaPorId() {
        // Arrange
        Long estudianteId = 204243174L;
        Long cuotaId = 2L;
        // Configura el comportamiento esperado del cuotaRepository
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setRut(estudianteId);
        estudianteMock.setNombres("Leonardo Iván");
        estudianteMock.setApellidos("Espinoza Ortiz");
        estudianteMock.setTipo_colegio_proc("Municipal");
        estudianteMock.setEgreso(2019);
        estudianteMock.setCantidad_cuotas(3);

        when(estudianteService.obtenerEstudianteporRut(estudianteId)).thenReturn(estudianteMock);

        Cuota cuotaMock = new Cuota();
        cuotaMock.setIdcuota(cuotaId);
        cuotaMock.setEstudiante(estudianteMock);
        cuotaMock.setEstado("Pendiente");

        when(cuotaRepository.findCuotaByIdcuota(cuotaId)).thenReturn(cuotaMock);
        // Act
        Cuota cuotaObtenida = cuotaService.obtenerCuotaPorId(cuotaId);
        // Assert
        // Verifica que se obtenga la cuota con el ID esperado
        assertEquals(cuotaMock, cuotaObtenida);
    }


    @Test
    public void testObtenerCuotasPorEstudianteId() {
        // Arrange
        Long estudianteId = 204243174L;

        // Configura el comportamiento esperado del estudianteService
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setRut(estudianteId);
        estudianteMock.setNombres("Leonardo Iván");
        estudianteMock.setApellidos("Espinoza Ortiz");
        estudianteMock.setTipo_colegio_proc("Municipal");
        estudianteMock.setEgreso(2019);
        estudianteMock.setCantidad_cuotas(3);

        when(estudianteService.obtenerEstudianteporRut(estudianteId)).thenReturn(estudianteMock);

        // Configura el comportamiento esperado del cuotaRepository
        Cuota cuota1 = new Cuota();
        cuota1.setIdcuota(1L);
        cuota1.setEstudiante(estudianteMock);
        cuota1.setEstado("Pendiente");

        Cuota cuota2 = new Cuota();
        cuota2.setIdcuota(2L);
        cuota2.setEstudiante(estudianteMock);
        cuota2.setEstado("Pendiente");

        Cuota cuota3 = new Cuota();
        cuota3.setIdcuota(3L);
        cuota3.setEstudiante(estudianteMock);
        cuota3.setEstado("Pendiente");

        List<Cuota> cuotasMock = Arrays.asList(cuota1, cuota2, cuota3);
        when(cuotaRepository.findByEstudiante_Rut(estudianteId)).thenReturn(cuotasMock);

        // Act
        List<Cuota> cuotasObtenidas = cuotaService.obtenerCuotasPorEstudianteId(estudianteId);

        // Assert
        // Verifica que se obtengan las tres cuotas para el estudiante
        assertEquals(cuotasMock, cuotasObtenidas);
    }

    @Test
    public void testActualizarCuota() {
        // Arrange
        Long cuotaId = 1L;
        Cuota cuotaPendiente = new Cuota();
        cuotaPendiente.setIdcuota(cuotaId);
        cuotaPendiente.setEstado("Pagado");
        // Configura el comportamiento esperado del repositorio
        when(cuotaRepository.findById(cuotaId)).thenReturn(Optional.of(cuotaPendiente));
        when(cuotaRepository.save(cuotaPendiente)).thenReturn(cuotaPendiente);
        // Act
        Cuota cuotaActualizada = cuotaService.actualizarCuota(cuotaPendiente);
        // Assert
        // Verifica que el estado de la cuota se haya actualizado correctamente
        assertEquals("Pagado", cuotaActualizada.getEstado());
        // Verifica que el método save se llamó exactamente una vez
        verify(cuotaRepository, times(1)).save(cuotaPendiente);
    }

    @Test
    public void testAplicarDescuentoPorPromedioExamenParaTodos() {
        // Arrange
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setRut(1L);
        estudiante1.setPromedio_examen(840);

        Estudiante estudiante2 = new Estudiante();
        estudiante2.setRut(2L);
        estudiante2.setPromedio_examen(950);

        List<Estudiante> estudiantes = Arrays.asList(estudiante1, estudiante2);

        // Configurar el comportamiento esperado del servicio de estudiantes
        when(estudianteService.getEstudiantes()).thenReturn(estudiantes);

        Cuota cuota1 = new Cuota();
        cuota1.setDescuento(0);
        cuota1.setMonto(1000);

        Cuota cuota2 = new Cuota();
        cuota2.setDescuento(0);
        cuota2.setMonto(1000);

        // Configurar el comportamiento esperado del repositorio de cuotas
        when(cuotaRepository.findByEstudiante_RutAndEstado(1L, "Pendiente")).thenReturn(Collections.singletonList(cuota1));
        when(cuotaRepository.findByEstudiante_RutAndEstado(2L, "Pendiente")).thenReturn(Collections.singletonList(cuota2));

        // Act
        cuotaService.aplicarDescuentoPorPromedioExamenParaTodos();

        // Assert
        // Verificar que el descuento se ha aplicado correctamente para el estudiante con promedio 840
        assertEquals(0, cuota1.getDescuento()); // 0% de descuento por promedio
        assertEquals(1000, cuota1.getMonto()); // 1000 - 0

        // Verificar que el descuento se ha aplicado correctamente para el estudiante con promedio 950
        assertEquals(100, cuota2.getDescuento()); // 10% de descuento por promedio
        assertEquals(900, cuota2.getMonto()); // 1000 - 100
    }


    @Test
    public void testObtenerCuotasPagadasPorEstudianteId() {
        // Arrange
        Long rutEstudiante = 12345L;
        List<Cuota> cuotasPagadas = Arrays.asList(new Cuota(), new Cuota());

        // Configura el comportamiento esperado del repositorio
        when(cuotaRepository.findByEstudiante_RutAndEstado(rutEstudiante, "Pagado")).thenReturn(cuotasPagadas);

        // Act
        List<Cuota> cuotasObtenidas = cuotaService.obtenerCuotasPagadasPorEstudianteId(rutEstudiante);

        // Assert
        assertEquals(2, cuotasObtenidas.size());
        verify(cuotaRepository, times(1)).findByEstudiante_RutAndEstado(rutEstudiante, "Pagado");
    }

    @Test
    public void testObtenerCuotasPendientesPorEstudianteId() {
        // Arrange
        Long rutEstudiante = 12345L;
        List<Cuota> cuotasPendientes = Arrays.asList(new Cuota(), new Cuota());

        // Configura el comportamiento esperado del repositorio
        when(cuotaRepository.findByEstudiante_RutAndEstado(rutEstudiante, "Pendiente")).thenReturn(cuotasPendientes);

        // Act
        List<Cuota> cuotasObtenidas = cuotaService.obtenerCuotasPendientesPorEstudianteId(rutEstudiante);

        // Assert
        assertEquals(2, cuotasObtenidas.size());
        verify(cuotaRepository, times(1)).findByEstudiante_RutAndEstado(rutEstudiante, "Pendiente");
    }





}


