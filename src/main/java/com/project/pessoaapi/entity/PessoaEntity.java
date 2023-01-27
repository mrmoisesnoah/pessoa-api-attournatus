package com.project.pessoaapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PESSOA")
public class PessoaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQ")
    @SequenceGenerator(name = "PESSOA_SEQ", sequenceName = "SEQ_PESSOA2", allocationSize = 1)
    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PESSOA_ENDERECO",
            joinColumns = @JoinColumn(name = "ID_PESSOA"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO"))
    private Set<EnderecoEntity> enderecoEntity;

}
