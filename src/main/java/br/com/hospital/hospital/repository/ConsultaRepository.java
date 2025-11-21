package br.com.hospital.hospital.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hospital.hospital.entity.Consulta;
import br.com.hospital.hospital.entity.Paciente;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer>{

    Paciente save(Paciente paciente);
    List<Consulta> findByPaciente(Paciente paciente);
    @Query("SELECT c.medico.nomeMedico, COUNT(c) FROM Consulta c " +
           "WHERE c.dataehoraConsulta BETWEEN :dataInicio AND :dataFim " + 
           "GROUP BY c.medico.nomeMedico")
    List<Object[]> countConsultasByMedicoAndPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.dataehoraConsulta BETWEEN :inicioDoDia AND :fimDoDia")
int countConsultasDoDia(@Param("inicioDoDia") LocalDateTime inicioDoDia, @Param("fimDoDia") LocalDateTime fimDoDia);
}