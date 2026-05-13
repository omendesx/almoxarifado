package com.almoxarifado.almoxarifado.services;

import com.almoxarifado.almoxarifado.Dtos.MovimentacaoDTO;
import com.almoxarifado.almoxarifado.entities.Movimentacao;
import com.almoxarifado.almoxarifado.entities.Produto;
import com.almoxarifado.almoxarifado.entities.Movimentacao;
import com.almoxarifado.almoxarifado.repositories.MovimentacaoRepository;
import com.almoxarifado.almoxarifado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    // Registrar movimentação de estoque
    @Transactional
    public Movimentacao registrarMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        // Buscar produto
        Produto produto = produtoRepository.findById(movimentacaoDTO.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        // Validar se há estoque suficiente para saída
        if (movimentacaoDTO.getTipo().equals("SAIDA")) {
            if (produto.getQuantidade() < movimentacaoDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente! Disponível: " + 
                    produto.getQuantidade() + ", Solicitado: " + movimentacaoDTO.getQuantidade());
            }
            // Atualizar quantidade (saída)
            produto.setQuantidade(produto.getQuantidade() - movimentacaoDTO.getQuantidade());
        } else {
            // Atualizar quantidade (entrada)
            produto.setQuantidade(produto.getQuantidade() + movimentacaoDTO.getQuantidade());
        }
        
        // Salvar produto atualizado
        produtoRepository.save(produto);
        
        // Criar registro de movimentação
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(movimentacaoDTO.getQuantidade());
        movimentacao.setTipo(Movimentacao.valueOf(movimentacaoDTO.getTipo()));
        movimentacao.setObservacao(movimentacaoDTO.getObservacao());
        movimentacao.setUsuarioResponsavel(movimentacaoDTO.getUsuarioResponsavel());
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        
        return movimentacaoRepository.save(movimentacao);
    }
    
    // Listar todas as movimentações
    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }
    
    // Listar movimentações de um produto específico
    public List<Movimentacao> movimentacoesPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId);
    }
    
    // Listar movimentações por período
    public List<Movimentacao> movimentacoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.findByDataMovimentacaoBetween(inicio, fim);
    }
}