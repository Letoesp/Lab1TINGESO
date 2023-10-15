package com.example.PreuTopEducation.Services;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Entities.Examen;
import com.example.PreuTopEducation.Repositories.ExamenRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExamenService {

    private final ExamenRepository examenRepository;
    private final EstudianteService estudianteService;

    @Autowired
    public ExamenService(ExamenRepository examenRepository, EstudianteService estudianteService) {
        this.examenRepository = examenRepository;
        this.estudianteService = estudianteService;
    }

    public void cargarNotasDesdeCSV(MultipartFile archivo) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                Long rutEstudiante = Long.parseLong(csvRecord.get("rutEstudiante"));
                String fechaExamen = csvRecord.get("fechaExamen");
                LocalDate fecha = LocalDate.parse(fechaExamen); // Asegúrate de que el formato de fecha en el CSV sea correcto
                int puntaje = Integer.parseInt(csvRecord.get("puntajeObtenido"));

                // Buscar estudiante por rut
                Estudiante estudiante = estudianteService.obtenerEstudianteporRut(rutEstudiante);

                // Crea un objeto Examen y guárdalo en la base de datos
                Examen examen = new Examen();
                examen.setEstudiante(estudiante);
                examen.setFecha(fecha);
                examen.setPuntaje(puntaje);
                examenRepository.save(examen);
            }
        }
    }



    public List<Examen> obtenerExamenesPorEstudiante(Long rutEstudiante) {
        return examenRepository.findExamenByEstudiante_Rut(rutEstudiante);
    }

    public void calcularYActualizarPromedioParaTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.getEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            List<Examen> examenes = obtenerExamenesPorEstudiante(estudiante.getRut());
            int sumPuntajes = examenes.stream().mapToInt(Examen::getPuntaje).sum();
            int totalExamenes = examenes.size();
            int promedio = (totalExamenes == 0) ? 0 : sumPuntajes / totalExamenes;
            estudiante.setPromedio_examen(promedio);
            estudianteService.actualizarEstudiante(estudiante); // Guarda el promedio en la base de datos
        }
    }

}

