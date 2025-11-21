package br.com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPrescricao;

    @ManyToOne
    @JoinColumn(name = "idConsultaFk", nullable = true)
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "idInternacaoFk", nullable = true)
    private Internacao internacao;

    @ManyToOne
    @JoinColumn(name = "idMedicamentoFk", nullable = false)
    private Medicamento medicamento;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false) // Ajuste o nome da coluna no banco se for diferente
    private Paciente paciente;

    @Column(nullable = false, length = 50)
    private String dose;

    @Column(nullable = false, length = 50)
    private String frequencia;

    @Column(nullable = false, length = 50)
    private String duracao;

}
