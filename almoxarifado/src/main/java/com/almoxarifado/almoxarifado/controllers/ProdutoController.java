package com.almoxarifado.almoxarifado.controllers;

import com.almoxarifado.almoxarifado.Dtos.ProdutoDTO;
import com.almoxarifado.almoxarifado.entities.Produto;
import com.almoxarifado.almoxarifado.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    // GET - Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }
    
    // GET - Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }
    
    // GET - Buscar por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable String codigo) {
        Produto produto = produtoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(produto);
    }
    
    // GET - Produtos em estoque baixo
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> produtosEmEstoqueBaixo() {
        List<Produto> produtos = produtoService.produtosEmEstoqueBaixo();
        return ResponseEntity.ok(produtos);
    }
    
    // GET - Produtos por categoria
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Produto>> produtosPorCategoria(@PathVariable Long categoriaId) {
        List<Produto> produtos = produtoService.produtosPorCategoria(categoriaId);
        return ResponseEntity.ok(produtos);
    }
    
    // POST - Criar novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto novoProduto = produtoService.criarProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
    
    // PUT - Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(
            @PathVariable Long id, 
            @Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }
    
    // DELETE - Deletar produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}