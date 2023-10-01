package com.example.PreuTopEducation.Controllers;

import com.example.PreuTopEducation.Repositories.EstudianteRepository;
import org.springframework.ui.Model;
import com.example.PreuTopEducation.Entities.Estudiante;
import com.example.PreuTopEducation.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller//para solicitudes html
@RequestMapping
public class EstudianteController {
    private final EstudianteService estudianteService;
    @Autowired //inyectar clase en constructor
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/estudiantes")//Leer todos los estudiantes en pantalla
    public String getEstudiantes(Model model) {
        model.addAttribute("estudiantes",estudianteService.getEstudiantes());
        return "estudiantes";//retorno a html estudiantes
    }

    @GetMapping("/ingreso_estudiante")//Recibe el objeto estudiante al formulario para trabajar con él
    public String registroEstudiante(Model model) {
        Estudiante estudiante = new Estudiante();
        model.addAttribute("estudiante", estudiante); // Agrega el objeto estudiante al modelo con el nombre "estudiante"
        return "ingreso_estudiante";
    }
    @PostMapping("/ingreso_estudiante")//Post para ingresar datos del estudiante al sistema.
    public String registrarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante){
        estudianteService.registrarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    @GetMapping("/editar_estudiante/{id}")//get para recibir el objeto estudiante a editar para editarlo
    public String editarEstudiante(@PathVariable Long id, Model model){
        model.addAttribute("estudiante",estudianteService.obtenerEstudianteporId(id));
        return "editar_estudiante";
    }

    @PostMapping("editar_estudiante/{id}")//post para realizar los cambios y actualizar el estudiante
    public String editarEstudiante(@PathVariable Long id,@ModelAttribute("estudiante") Estudiante estudiante,Model modelo) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporId(id);
        estudianteIngresado.setId(id);
        estudianteIngresado.setNombres(estudiante.getNombres());
        estudianteIngresado.setRut(estudiante.getRut());
        estudianteIngresado.setApellidos(estudiante.getApellidos());
        estudianteIngresado.setFecha_nacimiento(estudiante.getFecha_nacimiento());
        estudianteIngresado.setTipo_colegio_proc(estudiante.getTipo_colegio_proc());
        estudianteIngresado.setNombre_colegio(estudiante.getNombre_colegio());
        estudianteIngresado.setEgreso(estudiante.getEgreso());
        estudianteService.actualizarEstudiante(estudianteIngresado);
        return "redirect:/estudiantes";//una vez hecho el cambio, vuelve a la pagina de estudiantes
    }
    @GetMapping("/estudiantes/{id}")//Borrar estudiante
    public String eliminarEstudiante(@PathVariable Long id){
        estudianteService.eliminarEstudiante(id);
        return "redirect:/estudiantes";

    }

    @GetMapping("/generar_cuota") // Leer estudiantes con tipoPago igual a "Cuota"
    public String getEstudiantesCuota(Model model) {
        List<Estudiante> estudiantes = estudianteService.getEstudianteporTipopago("Cuotas");
        System.out.println("Número de estudiantes con tipo de pago Cuota: " + estudiantes.size());
        model.addAttribute("estudiantes", estudiantes);
        return "generar_cuota"; // Retorna a la vista HTML estudiantes
    }
    @PostMapping("generar_cuota/{id}")//post para realizar los cambios y actualizar las cuotas
    public String ingresarcuotaEstudiante(@PathVariable Long id,@ModelAttribute("estudiante") Estudiante estudiante,Model modelo) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporId(id);
        estudianteIngresado.setCantidad_cuotas(estudiante.getCantidad_cuotas());
        estudianteService.actualizarEstudiante(estudianteIngresado);
        return "generar_cuota";
    }




}






