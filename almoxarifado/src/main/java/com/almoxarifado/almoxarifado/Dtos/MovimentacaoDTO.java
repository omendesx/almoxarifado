package com.almoxarifado.almoxarifado.Dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MovimentacaoDTO {
    
    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
    
    @NotBlank(message = "Tipo de movimentação é obrigatório")
    @Pattern(regexp = "ENTRADA|SAIDA", message = "Tipo deve ser ENTRADA ou SAIDA")
    private String tipo;
    
    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    private String observacao;
    
    private String usuarioResponsavel;
}