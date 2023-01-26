package com.project.pessoaapi.dto.enderecodto;


import com.project.pessoaapi.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EnderecoCreateDTO {
    private Integer idPessoa;
    @NotNull
    @Schema(description = "Tipo de Endereço. Campo não pode ser vazio.", example = "RESIDENCIAL")
    private TipoEndereco tipo;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 250)
    @Schema(description = "Espaço publico ou privado, para referência residêncial. Campo não pode ser vazio.", example = "Rua Azul")
    private String logradouro;
    @NotNull
    @Schema(description = "Numero do endereço residencial ou comercial. Campo não pode ser vazio.", example = "13")
    private Integer numero;
    @NotNull
    @Schema(description = "Referencias, local ou tipo de residencia. Campo não pode ser vazio.", example = "Apartamento 04")
    private String complemento;
    @NotNull
    @Size(max = 8, min = 8)
    @Schema(description = "Código de Enderaçamento Postal. Minimo de 8 digitos. Campo não pode ser vazio.", example = "72000123")
    private String cep;
    @NotNull
    @NotEmpty
    @Size(max = 250)
    @Schema(description = "Cidade onde se localiza o endereço a ser cadastrado. Campo não pode ser vazio.", example = "Ceilândia")
    private String cidade;
    @NotNull
    @Schema(description = "Estado onde se localiza o endereço a ser cadastrado. Campo não pode ser vazio.", example = "Maranhão")
    private String estado;
    @NotNull
    @Schema(description = "Pais onde se localiza o endereço a ser cadastrado. Campo não pode ser vazio.", example = "Brasil")
    private String pais;
}
