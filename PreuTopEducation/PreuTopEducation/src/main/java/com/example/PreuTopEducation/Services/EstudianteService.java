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
    HashMap<String,Object> datos;
    private final EstudianteRepository estudianteRepository;
    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository){//constructor e inyección de dependencia
        this.estudianteRepository = estudianteRepository;
    }
    public List<Estudiante> getEstudiantes(){//obtener todos los estudiantes
        return this.estudianteRepository.findAll();
    }
    public ResponseEntity<Object> newEstudiante(Estudiante estudiante) {//crear estudiante
        Optional<Estudiante> res= estudianteRepository.findEstudianteByNombresAndApellidos(estudiante.getNombres(), estudiante.getApellidos());
        datos = new HashMap<>();


        if(res.isPresent() && estudiante.getRut()==null){
            datos.put("error",true);
            datos.put("message","Ya existe un estudiante registrado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message","Se ha registrado el estudiante correctamente");
        if (estudiante.getRut()!=null){//condicional para agregar el update
            datos.put("message","Se han actualizado los datos del estudiante correctamente");

        }
        estudianteRepository.save(estudiante);
        datos.put("data",estudiante);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );

    }
    public ResponseEntity<Object> deleteestudiante(Long rut){
        datos = new HashMap<>();
        boolean existe=this.estudianteRepository.existsById(rut);
        if (!existe){
            datos.put("error",true);
            datos.put("message","No existe un estudiante con ese rut");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );

        }
        estudianteRepository.deleteById(rut);
        datos.put("message","Estudiante eliminado con exito");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}
