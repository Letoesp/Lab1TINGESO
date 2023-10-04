package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CuotaRepository extends JpaRepository<Cuota,Long> {
    Cuota findCuotaByIdcuota(Long idcuota);

    List<Cuota> findByEstudiante_Rut(Long estudianteId);


}
