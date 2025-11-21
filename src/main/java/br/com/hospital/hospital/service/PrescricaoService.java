package br.com.hospital.hospital.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.hospital.hospital.entity.Prescricao;
import br.com.hospital.hospital.repository.PrescricaoRepository;

@Service
public class PrescricaoService {

    @Autowired
    private PrescricaoRepository prescricaoRepository;

    // Salvar
    public Prescricao save(Prescricao prescricao) {
        return prescricaoRepository.save(prescricao);
    }

    // Listar
    public List<Prescricao> findAll() {
        return prescricaoRepository.findAll();
    }

    // Buscar por ID
    public Prescricao findById(Integer id) {
        return prescricaoRepository.findById(id).orElse(null);
    }

    // Excluir
    public void deleteById(Integer id) {
        prescricaoRepository.deleteById(id);
    }
}
