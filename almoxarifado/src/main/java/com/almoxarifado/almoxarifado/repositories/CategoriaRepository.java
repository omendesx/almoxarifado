package com.almoxarifado.almoxarifado.repositories;

import com.almoxarifado.almoxarifado.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Buscar categoria por nome
    Optional<Categoria> findByNome(String nome);
    
    // Verificar se categoria existe
    boolean existsByNome(String nome);
}