package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuotaRepository extends JpaRepository<Cuota,Long> {
    Optional<Cuota> findCuotaByIdcuota(Long idcuota);
}
