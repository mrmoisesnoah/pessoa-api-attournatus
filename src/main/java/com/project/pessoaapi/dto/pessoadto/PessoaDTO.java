package com.project.pessoaapi.dto.pessoadto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaDTO extends PessoaCreateDTO {
    private Integer idPessoa;
}
