package com.example.PreuTopEducation.Services;

import com.example.PreuTopEducation.Entities.Cuota;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    private final EstudianteService estudianteService;

    @Autowired
    public CuotaService(CuotaRepository cuotaRepository,EstudianteService estudianteService) {
        this.cuotaRepository = cuotaRepository;
        this.estudianteService = estudianteService;
    }
//metodo que se comunica con el servicio estudiante para obtener un estudiante por su rut
    public Estudiante obtenerEstudiantePorId(Long estudianteId) {
        return estudianteService.obtenerEstudianteporRut(estudianteId);
    }

    public void generarCuotasporId(Long id) {
        // Seobtiene el estudiante por su ID(rut) utilizando el servicio de Estudiante
        Estudiante estudiante = obtenerEstudiantePorId(id);
        double arancelTotal = 1500000.0; // Se utiliza arancel fijo de 1.500.000

        // Calculo descuento según tipo de colegio de procedencia
        double descuentoTipo = 0;
        if ("Municipal".equals(estudiante.getTipo_colegio_proc())) {
            descuentoTipo = 0.2 * arancelTotal; // 20% de descuento
        } else if ("Subvencionado".equals(estudiante.getTipo_colegio_proc())) {
            descuentoTipo = 0.1 * arancelTotal; // 10% de descuento
        }

        // Calculo descuento según años desde que egresó del colegio
        int anosDesdeEgreso = Year.now().getValue() - estudiante.getEgreso();
        double descuentoAnios = 0;
        if (anosDesdeEgreso < 1) {
            descuentoAnios = 0.15 * arancelTotal; // 15% de descuento
        } else if (anosDesdeEgreso >= 1 && anosDesdeEgreso <= 2) {
            descuentoAnios = 0.08 * arancelTotal; // 8% de descuento
        } else if (anosDesdeEgreso >= 3 && anosDesdeEgreso <= 4) {
            descuentoAnios = 0.04 * arancelTotal; // 4% de descuento
        }

        // Se aplica ambos descuentos al arancel total
        arancelTotal -= (descuentoTipo + descuentoAnios);

        // Calculo número de cuotas que debe pagar el estudiante
        int cantidadCuotas = estudiante.getCantidad_cuotas();
        int montoCuota = (int) Math.ceil(arancelTotal / cantidadCuotas); // Redondeo hacia arriba

        // Obtener el mes y año actual
        YearMonth yearMonth = YearMonth.now();
        LocalDate fechaActual = LocalDate.now();

        // Verificación si la fecha actual es después del plazo de pago del mes actual
        if (fechaActual.getDayOfMonth() > 10) {
            // Avanza al siguiente mes si la fecha actual es después del plazo de pago
            yearMonth = yearMonth.plusMonths(1);
        }

        // Generar cuotas y guardar en la base de datos
        for (int i = 0; i < cantidadCuotas; i++) {
            LocalDate plazoInicio = yearMonth.atDay(5);
            LocalDate plazoFinal = yearMonth.atDay(10);


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




    public Cuota obtenerCuotaPorId(Long cuotaId) {
        return cuotaRepository.findCuotaByIdcuota(cuotaId);

    }

    public List<Cuota> obtenerCuotasPorEstudianteId(Long estudianteId) {
        return cuotaRepository.findByEstudiante_Rut(estudianteId);
    }

    public Cuota actualizarCuota(Cuota cuota) {

        return cuotaRepository.save(cuota);
    }

    public void aplicarDescuentoPorPromedioExamenParaTodos() {
        List<Estudiante> estudiantes = estudianteService.getEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            double descuentoPorcentaje = 0.0;

            if (estudiante.getPromedio_examen() >= 950) {
                descuentoPorcentaje = 0.10; // 10% de descuento
            } else if (estudiante.getPromedio_examen() >= 900) {
                descuentoPorcentaje = 0.05; // 5% de descuento
            } else if (estudiante.getPromedio_examen() >= 850) {
                descuentoPorcentaje = 0.02; // 2% de descuento
            }

            // Obtener las cuotas pendientes del estudiante
            List<Cuota> cuotas = cuotaRepository.findByEstudiante_RutAndEstado(estudiante.getRut(), "Pendiente");

            // Aplicar descuento a las cuotas y actualizar en la base de datos
            for (Cuota cuota : cuotas) {
                int descuentoActual = cuota.getDescuento(); // Obtener el descuento acumulado
                int montoOriginal = cuota.getMonto();
                double nuevoDescuento = montoOriginal * descuentoPorcentaje; // Calcular nuevo descuento

                // Sumar el nuevo descuento al descuento acumulado
                descuentoActual += nuevoDescuento;

                // Aplicar el descuento acumulado al monto de la cuota
                int montoConDescuento = (int) Math.ceil(montoOriginal - descuentoActual);

                // Actualizar el atributo descuento de la cuota
                cuota.setDescuento(descuentoActual);

                // Actualizar el monto de la cuota con el descuento acumulado
                cuota.setMonto(montoConDescuento);

                // Guardar la cuota actualizada en la base de datos
                cuotaRepository.save(cuota);
            }
        }
    }


    public List<Cuota> obtenerCuotasPagadasPorEstudianteId(Long rutEstudiante) {
        return cuotaRepository.findByEstudiante_RutAndEstado(rutEstudiante, "Pagado");
    }

    public List<Cuota> obtenerCuotasPendientesPorEstudianteId(Long rutEstudiante) {
        return cuotaRepository.findByEstudiante_RutAndEstado(rutEstudiante,"Pendiente");
    }
}
















