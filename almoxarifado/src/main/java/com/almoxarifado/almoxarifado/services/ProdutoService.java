package com.almoxarifado.almoxarifado.services;

import com.almoxarifado.almoxarifado.Dtos.ProdutoDTO;
import com.almoxarifado.almoxarifado.entities.Categoria;
import com.almoxarifado.almoxarifado.entities.Produto;
import com.almoxarifado.almoxarifado.repositories.CategoriaRepository;
import com.almoxarifado.almoxarifado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    // Listar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    // Buscar produto por ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }
    
    // Buscar produto por código
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com código: " + codigo));
    }
    
    // Criar novo produto
    @Transactional
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        // Verificar se código já existe
        if (produtoRepository.findByCodigo(produtoDTO.getCodigo()).isPresent()) {
            throw new RuntimeException("Já existe um produto com o código: " + produtoDTO.getCodigo());
        }
        
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCodigo(produtoDTO.getCodigo());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeMinima(produtoDTO.getQuantidadeMinima());
        
        // Associar categoria se fornecida
        if (produtoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }
        
        return produtoRepository.save(produto);
    }
    
    // Atualizar produto
    @Transactional
    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarPorId(id);
        
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeMinima(produtoDTO.getQuantidadeMinima());
        
        // Atualizar categoria se necessário
        if (produtoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }
        
        return produtoRepository.save(produto);
    }
    
    // Deletar produto
    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
    
    // Buscar produtos em estoque baixo
    public List<Produto> produtosEmEstoqueBaixo() {
        return produtoRepository.findProdutosEmEstoqueBaixo();
    }
    
    // Buscar produtos por categoria
    public List<Produto> produtosPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }
}