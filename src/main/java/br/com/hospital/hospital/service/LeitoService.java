package br.com.hospital.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hospital.hospital.entity.Leito;
import br.com.hospital.hospital.repository.LeitoRepository;
import br.com.hospital.hospital.repository.InternacaoRepository; // ✅ Adicionar este import

@Service
public class LeitoService {

    @Autowired
    private LeitoRepository leitoRepository;
    
    @Autowired // ✅ Injetar o InternacaoRepository para calcular a ocupação
    private InternacaoRepository internacaoRepository;

    // --- CRUD BÁSICO ---
    // ... (Métodos save, findAll, findById, deleteById - MANTIDOS) ...
    public Leito save(Leito leito) {
        return leitoRepository.save(leito);
    }
    public List<Leito> findAll() {
        return leitoRepository.findAll();
    }
    public Leito findById(Integer id) {
        return leitoRepository.findById(id).orElse(null);
    }
    public void deleteById(Integer id) {
        leitoRepository.deleteById(id);
    }
    // ... FIM DOS MÉTODOS MANTIDOS ...

    // --- LÓGICA DE NEGÓCIO ---

    public List<Leito> findAllExcludingIds(List<Integer> leitoIdsExcluir) {
        if (leitoIdsExcluir == null || leitoIdsExcluir.isEmpty()) {
            return leitoRepository.findAll();
        }
        
        return leitoRepository.findByIdQuartoNotIn(leitoIdsExcluir);
    }
    
    // ✅ NOVO MÉTODO PARA RELATÓRIO: Conta o total de leitos
    public long countAllLeitos() {
        return leitoRepository.count();
    }
    
    // ✅ NOVO MÉTODO PARA RELATÓRIO: Conta o total de leitos ocupados
    public long countLeitosOcupados() {
        // Assume-se que 'Ativa' é o status da Internação que indica a ocupação
        return internacaoRepository.findIdQuartoByStatus("Ativa").size();
    }

    // ✅ NOVO MÉTODO PARA RELATÓRIO: Calcula a taxa de ocupação
    public double calcularTaxaOcupacaoAtual() {
        long totalLeitos = countAllLeitos();
        long leitosOcupados = countLeitosOcupados();

        if (totalLeitos == 0) {
            return 0.0;
        }

        // Cálculo: (Ocupados / Total) * 100
        return ((double) leitosOcupados / totalLeitos) * 100.0;
    }
    
    public Object findAvailable() {
        throw new UnsupportedOperationException("Unimplemented method 'findAvailable'");
    }

    public int contarLeitosDisponiveis() {
    return leitoRepository.countLeitosDisponiveis();
}

}