package com.project.pessoaapi.dto.enderecodto;


import com.project.pessoaapi.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCreateDTO {

    @NotNull
    @Schema(description = "Tipo de Endereço. Campo não pode ser vazio.", example = "RESIDENCIAL")
    private TipoEndereco tipo;
    @NotNull
    @NotBlank
    @Size(max = 250)
    @Schema(description = "Espaço publico ou privado, para referência residêncial. Campo não pode ser vazio.", example = "Rua Azul")
    private String logradouro;
    @NotNull
    @Schema(description = "Numero do endereço residencial ou comercial. Campo não pode ser vazio.", example = "13")
    private Integer numero;
    @NotNull
    @Size(max = 8, min = 8)
    @Schema(description = "Código de Enderaçamento Postal. Minimo de 8 digitos. Campo não pode ser vazio.", example = "72000123")
    private String cep;
    @NotNull
    @NotBlank
    @Size(max = 250)
    @Schema(description = "Cidade onde se localiza o endereço a ser cadastrado. Campo não pode ser vazio.", example = "Ceilândia")
    private String cidade;

}
