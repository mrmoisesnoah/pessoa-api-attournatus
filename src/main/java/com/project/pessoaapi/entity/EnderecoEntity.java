package com.project.pessoaapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pessoaapi.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ENDERECO")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECO_SEQ")
    @SequenceGenerator(name = "ENDERECO_SEQ", sequenceName = "SEQ_ID_ENDERECO", allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;
    @Column(name = "TIPO")
    private TipoEndereco tipo;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "NUMERO")
    private Integer numero;
    @Column(name = "CIDADE")
    private String cidade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PESSOA_ENDERECO",
            joinColumns = @JoinColumn(name = "ID_ENDERECO"),
            inverseJoinColumns = @JoinColumn(name = "ID_PESSOA"))
    private Set<PessoaEntity> pessoaEntity;



}
