package com.example.PreuTopEducation.Services;
import com.example.PreuTopEducation.Repositories.ExamenRepository;

import org.springframework.stereotype.Service;
i
@Service
public class ExamenService {

    private final ExamenRepository examenRepository;
    public ExamenService(ExamenRepository examenRepository, EstudianteService estudianteService) {
        this.examenRepository = examenRepository;

    }


}

