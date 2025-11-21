package br.com.hospital.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hospital.hospital.entity.Leito;

public interface LeitoRepository extends JpaRepository<Leito, Integer>{

    List<Leito> findByDisponivelTrue();
    
    // 🚨 CORREÇÃO: Mudando 'IdLeito' para 'IdQuarto' no nome do método
    // O tipo do parâmetro (List<Integer>) permanece o mesmo.
    List<Leito> findByIdQuartoNotIn(List<Integer> leitoIds);

    @Query("SELECT COUNT(l) FROM Leito l WHERE l.status = 'Disponível'") 
int countLeitosDisponiveis();

}