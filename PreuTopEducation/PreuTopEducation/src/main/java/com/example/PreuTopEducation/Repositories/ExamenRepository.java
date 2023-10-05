package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    // Puedes agregar consultas específicas de Examen si las necesitas en el futuro
}
