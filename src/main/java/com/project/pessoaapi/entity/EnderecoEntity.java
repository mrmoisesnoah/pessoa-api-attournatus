package com.project.pessoaapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pessoaapi.enums.TipoEndereco;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "ENDERECO_PESSOA")
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
    @Column(name = "NUMERO")
    private Integer numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "CIDADE")
    private String cidade;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "PAIS")
    private String pais;
    @JsonIgnore
    @ManyToMany(mappedBy = "enderecoEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PessoaEntity> pessoaEntity;



}
