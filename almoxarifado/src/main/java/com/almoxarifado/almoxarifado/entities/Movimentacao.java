package com.almoxarifado.almoxarifado.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;
    
    @Column(length = 500)
    private String observacao;
    
    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao;
    
    @Column(name = "usuario_responsavel", length = 100)
    private String usuarioResponsavel;
    
    @PrePersist
    protected void onCreate() {
        dataMovimentacao = LocalDateTime.now();
    }

    public static TipoMovimentacao valueOf(String tipo2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'valueOf'");
    }
}

// Enum para tipos de movimentação
enum TipoMovimentacao {
    ENTRADA, SAIDA
}