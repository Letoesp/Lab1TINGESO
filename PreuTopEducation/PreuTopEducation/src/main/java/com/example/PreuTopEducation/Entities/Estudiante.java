package com.example.PreuTopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private String rut;
    private String nombres;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String tipo_colegio_proc;
    private String nombre_colegio;
    private int año_egreso;
    private String tipo_pago;
    private int arancel_estudiante;
    private int cantidad_cuotas;
    private int promedio_examen;


    public Estudiante(String rut, String nombres, String apellidos, LocalDate fecha_nacimiento, String tipo_colegio_proc, String nombre_colegio, int año_egreso) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tipo_colegio_proc = tipo_colegio_proc;
        this.nombre_colegio = nombre_colegio;
        this.año_egreso = año_egreso;
    }//constructor con datos para ingreso al sistema


}
