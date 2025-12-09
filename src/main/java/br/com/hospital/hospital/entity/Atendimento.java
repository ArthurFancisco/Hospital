package br.com.hospital.hospital.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAtendimento;

    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta idConsulta;

    @Column(nullable = false)
    private LocalDateTime dataHoraAtendimento = LocalDateTime.now();

    @Column(length = 500)
    private String queixaPrincipal;

    @Column(length = 1000)
    private String exameFisico;

    @Column(length = 200)
    private String diagnostico;

    @Column(length = 1500)
    private String planoTerapeutico;

    @Column(length = 1500)
    private String prescricaoMedicamentos;
}
