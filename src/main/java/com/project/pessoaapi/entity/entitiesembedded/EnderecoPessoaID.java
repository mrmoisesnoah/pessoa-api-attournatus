package com.project.pessoaapi.entity.entitiesembedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class EnderecoPessoaID implements Serializable {
    @Column(name = "ID_PESSOA")
    private Integer idPessoa;
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;
}
