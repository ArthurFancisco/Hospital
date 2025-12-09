package br.com.hospital.hospital.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Consulta {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idConsulta;
     
    @Column(nullable = false, length = 50)
    private LocalDateTime dataehoraConsulta;

    @Column(nullable = false, length = 100)
    private String observacoesConsulta;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idMedicoFk", nullable = false)
    private Medico medico;

    private String statusConsulta; // AGENDADA, ATENDIDA etc.

    // -----------------------
    // CAMPO QUE VAI PARA A VIEW
    // -----------------------
    @Transient
    private Atendimento atendimento;

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

}
