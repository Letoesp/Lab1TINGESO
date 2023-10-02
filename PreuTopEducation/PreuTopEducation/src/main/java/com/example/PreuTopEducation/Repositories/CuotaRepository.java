package com.example.PreuTopEducation.Repositories;

import com.example.PreuTopEducation.Entities.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CuotaRepository extends JpaRepository<Cuota,Long> {
    Optional<Cuota> findCuotaByIdcuota(Long idcuota);
}
