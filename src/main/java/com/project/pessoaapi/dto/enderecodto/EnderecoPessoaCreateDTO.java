package com.project.pessoaapi.dto.enderecodto;


import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoPessoaCreateDTO extends PessoaCreateDTO {

    private Integer idPessoa;
    private List<EnderecoDTO> enderecoDTOS;
}
