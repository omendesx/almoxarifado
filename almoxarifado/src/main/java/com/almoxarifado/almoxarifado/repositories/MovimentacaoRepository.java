package com.almoxarifado.almoxarifado.repositories;

import com.almoxarifado.almoxarifado.entities.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    
    // Buscar movimentações por produto
    List<Movimentacao> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);
    
    // Buscar movimentações por período
    List<Movimentacao> findByDataMovimentacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // Buscar movimentações por tipo
    List<Movimentacao> findByTipoOrderByDataMovimentacaoDesc(String tipo);
}