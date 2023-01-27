package com.project.pessoaapi.dto.pessoadto;

import com.project.pessoaapi.enums.TipoEndereco;
import lombok.Data;

@Data
public class PessoaEnderecoDTO {

    private Integer idEndereco;
    private TipoEndereco tipo;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String cidade;

}
