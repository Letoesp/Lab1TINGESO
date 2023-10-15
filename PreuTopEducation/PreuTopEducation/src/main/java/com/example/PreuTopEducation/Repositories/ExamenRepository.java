package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findExamenByEstudiante_Rut(Long rutEstudiante);
    // Puedes agregar consultas específicas de Examen si las necesitas en el futuro
}
