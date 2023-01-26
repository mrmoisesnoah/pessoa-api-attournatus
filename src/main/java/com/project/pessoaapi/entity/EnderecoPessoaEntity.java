package com.project.pessoaapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pessoaapi.entity.entitiesembedded.EnderecoPessoaID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PESSOA_X_PESSOA_ENDERECO")
public class EnderecoPessoaEntity {

    @EmbeddedId
    private EnderecoPessoaID enderecoPessoaPK;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPessoa")
    @JoinColumn(name = "ID_PESSOA")
    private PessoaEntity pessoaEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("idEndereco")
    @JoinColumn(name = "ID_ENDERECO")
    private EnderecoEntity enderecoEntity;
}
