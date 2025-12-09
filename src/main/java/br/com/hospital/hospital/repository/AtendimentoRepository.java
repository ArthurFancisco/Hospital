package br.com.hospital.hospital.repository;

import br.com.hospital.hospital.entity.Atendimento;
import br.com.hospital.hospital.entity.Consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {
    Optional<Atendimento> findByIdConsulta(Consulta consulta);
     void deleteByIdConsulta_IdConsulta(Integer idConsulta);
}