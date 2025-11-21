// PacienteService.java
package br.com.hospital.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hospital.hospital.entity.Paciente;
import br.com.hospital.hospital.entity.Usuario;
import br.com.hospital.hospital.repository.PacienteRepository;
import br.com.hospital.hospital.repository.UsuarioRepository; 
import br.com.hospital.hospital.DTO.PacienteCadastroDTO;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Paciente cadastrarNovoPaciente(PacienteCadastroDTO dto) {

        // 1. SALVAR USUÁRIO
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(dto.getUsername());
        novoUsuario.setPassword(dto.getPassword());
        novoUsuario.setRole("PACIENTE");

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);


        // 2. SALVAR PACIENTE
        Paciente novoPaciente = new Paciente();

        novoPaciente.setNomePaciente(dto.getNomePaciente());
        novoPaciente.setCpfPaciente(dto.getCpfPaciente());
        novoPaciente.setNascPaciente(dto.getNascPaciente());
        novoPaciente.setTelefonePaciente(dto.getTelefonePaciente());
        novoPaciente.setEnderecoPaciente(dto.getEnderecoPaciente());
        novoPaciente.setSexoPaciente(dto.getSexoPaciente());
        novoPaciente.setPesoPaciente(dto.getPesoPaciente());
        novoPaciente.setTipoSanguinioPaciente(dto.getTipoSanguinioPaciente());

        // Ligar o paciente ao usuário
        novoPaciente.setUsuario(usuarioSalvo);

        Paciente pacienteSalvo = pacienteRepository.save(novoPaciente);

        // 3. Parte de atualizar o usuário foi removida
        // Porque o Usuario NÃO possui relacionamento com Paciente.
        // Se quiser tornar bidirecional, basta pedir.

        return pacienteSalvo;
    }


    // Métodos auxiliares já existentes
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public void deleteById(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public Optional<Paciente> findById(Integer id) {
        return pacienteRepository.findById(id);
    }
}
