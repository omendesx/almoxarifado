package com.almoxarifado.almoxarifado.repositories;

import com.almoxarifado.almoxarifado.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // Buscar produto por código
    Optional<Produto> findByCodigo(String codigo);
    
    // Buscar produtos com quantidade abaixo do mínimo
    List<Produto> findByQuantidadeLessThanEqual(Integer quantidadeMinima);
    
    // Buscar produtos por nome (contendo)
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar produtos por categoria
    List<Produto> findByCategoriaId(Long categoriaId);
    
    // Buscar produtos com quantidade zerada
    List<Produto> findByQuantidade(Integer quantidade);
    
    // Consulta personalizada para produtos em baixa quantidade
    @Query("SELECT p FROM Produto p WHERE p.quantidade <= p.quantidadeMinima")
    List<Produto> findProdutosEmEstoqueBaixo();
}