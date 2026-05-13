package com.almoxarifado.almoxarifado.controllers;

import com.almoxarifado.almoxarifado.Dtos.MovimentacaoDTO;
import com.almoxarifado.almoxarifado.entities.Movimentacao;
import com.almoxarifado.almoxarifado.services.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {
    
    @Autowired
    private MovimentacaoService movimentacaoService;
    
    // GET - Listar todas movimentações
    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        return ResponseEntity.ok(movimentacoes);
    }
    
    // GET - Movimentações por produto
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Movimentacao>> movimentacoesPorProduto(@PathVariable Long produtoId) {
        List<Movimentacao> movimentacoes = movimentacaoService.movimentacoesPorProduto(produtoId);
        return ResponseEntity.ok(movimentacoes);
    }
    
    // POST - Registrar movimentação (entrada/saída)
    @PostMapping
    public ResponseEntity<Movimentacao> registrarMovimentacao(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = movimentacaoService.registrarMovimentacao(movimentacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }
}