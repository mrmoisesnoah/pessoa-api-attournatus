package com.project.pessoaapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pessoaapi.entity.entitiesembedded.EnderecoPessoaID;
import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PESSOA_ENDERECO")
public class EnderecoPessoaEntity {

    @EmbeddedId
    private EnderecoPessoaID enderecoPessoaPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPessoa")
    @JoinColumn(name = "ID_PESSOA")
    private PessoaEntity pessoaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEndereco")
    @JoinColumn(name = "ID_ENDERECO")
    private EnderecoEntity enderecoEntity;
}
