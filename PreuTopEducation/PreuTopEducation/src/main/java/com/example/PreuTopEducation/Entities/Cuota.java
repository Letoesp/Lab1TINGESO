package com.example.PreuTopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idcuota;
    @ManyToOne
    @JoinColumn(name = "id")
    private Estudiante estudiante;//llave foranea
    private LocalDate fecha_pago;
    private int monto;
    private String estado;
    private LocalDate plazo_inicio;
    private LocalDate plazo_final;
    private int interes;
    private int descuento;




}
