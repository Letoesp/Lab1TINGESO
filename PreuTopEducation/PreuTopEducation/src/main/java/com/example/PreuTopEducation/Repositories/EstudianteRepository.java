package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Long> {
    Optional<Estudiante> findEstudianteByRut(Long rut);


    List<Estudiante> findEstudiantesByTipopago(String tipopago);



}
