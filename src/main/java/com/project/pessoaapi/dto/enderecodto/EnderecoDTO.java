package com.project.pessoaapi.dto.enderecodto;

import com.project.pessoaapi.enums.TipoEndereco;
import lombok.Data;

@Data
public class EnderecoDTO {
    private String nomePessoa;
    private Integer idEndereco;
    private TipoEndereco tipo;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String cidade;

}
