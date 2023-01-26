package com.project.pessoaapi.dto.enderecodto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pessoaapi.entity.entitiesembedded.EnderecoPessoaID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnderecoPessoaDTO extends EnderecoPessoaCreateDTO {
    @JsonIgnore
    private EnderecoPessoaID enderecoPessoaPK;
}
