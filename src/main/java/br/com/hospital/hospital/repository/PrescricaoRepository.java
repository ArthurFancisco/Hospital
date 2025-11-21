package br.com.hospital.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hospital.hospital.entity.Paciente;
import br.com.hospital.hospital.entity.Prescricao;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Integer> {

    List<Prescricao> findByPaciente(Paciente paciente);

}
