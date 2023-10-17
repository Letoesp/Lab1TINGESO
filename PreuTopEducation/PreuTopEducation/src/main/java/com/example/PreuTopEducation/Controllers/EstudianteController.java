package com.example.PreuTopEducation.Controllers;

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

    @PostMapping("/ingreso_estudiante")
    public String registrarEstudiante(@RequestParam("rutDigits") String rutDigits,
                                      @RequestParam("rutVerifier") String rutVerifier,
                                      @ModelAttribute("estudiante") Estudiante estudiante, Model model) {
        // Combina los dígitos y el dígito verificador en un solo RUT
        String rutCombined = rutDigits + rutVerifier;

        // Convierte el RUT combinado a long y asigna al objeto Estudiante
        long rutLong = Long.parseLong(rutCombined);
        estudiante.setRut(rutLong);

        // Guarda el estudiante en la base de datos
        estudianteService.registrarEstudiante(estudiante);

        // Redirige a la página de estudiantes
        return "redirect:/estudiantes";
    }




    @GetMapping("/editar_estudiante/{id}")//get para recibir el objeto estudiante a editar para editarlo
    public String editarEstudiante(@PathVariable Long id, Model model){
        model.addAttribute("estudiante",estudianteService.obtenerEstudianteporRut(id));
        return "editar_estudiante";
    }

    @PostMapping("editar_estudiante/{id}")//post para realizar los cambios y actualizar el estudiante
    public String editarEstudiante(@PathVariable Long id,@ModelAttribute("estudiante") Estudiante estudiante,Model modelo) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporRut(id);
        estudianteIngresado.setRut(id);
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

    @GetMapping("/actualizar_cuota/{id}")//get para recibir el objeto estudiante a editar para editarlo
    public String cantidadcuotasdeEstudiante(@PathVariable Long id, Model model){
        model.addAttribute("estudiante",estudianteService.obtenerEstudianteporRut(id));
        return "actualizar_cuota";
    }
    @PostMapping("actualizar_cuota/{id}")//post para realizar los cambios y actualizar las cuotas
    public String ingresarcuotaEstudiante(@PathVariable Long id,@ModelAttribute("estudiante") Estudiante estudiante,Model modelo) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporRut(id);
        estudianteIngresado.setCantidad_cuotas(estudiante.getCantidad_cuotas());
        estudianteService.actualizarEstudiante(estudianteIngresado);
        return "redirect:/generar_cuota";
    }

    @GetMapping("/cuotas") // Leer estudiantes con tipoPago igual a "Cuota pero en la vista /cuotas"
    public String getEstudiantesconCuota(Model model) {
        List<Estudiante> estudiantes = estudianteService.getEstudianteporTipopago("Cuotas");
        System.out.println("Número de estudiantes con tipo de pago Cuota: " + estudiantes.size());
        model.addAttribute("estudiantes", estudiantes);
        return "cuotas"; // Retorna a la vista HTML cuotas
    }




}






