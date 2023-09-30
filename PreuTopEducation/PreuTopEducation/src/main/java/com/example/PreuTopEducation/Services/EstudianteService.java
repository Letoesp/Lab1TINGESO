package com.example.PreuTopEducation.Services;

import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }
    public List<Estudiante> getEstudiantes(){
        return this.estudianteRepository.findAll();
    }
    public Estudiante registrarEstudiante(Estudiante estudiante){
        return estudianteRepository.save(estudiante);
    }


}
