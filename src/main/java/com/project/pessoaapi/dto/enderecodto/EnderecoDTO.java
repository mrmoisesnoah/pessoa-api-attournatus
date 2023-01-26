package com.project.pessoaapi.dto.enderecodto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoDTO extends EnderecoCreateDTO {
    private Integer idEndereco;
    private Integer idPessoa;
}
