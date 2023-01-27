package com.project.pessoaapi.dto.pessoadto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PessoaDTO {
    private Integer idPessoa;
    private String nome;
    private LocalDate dataNascimento;
    private List<PessoaEnderecoDTO> enderecoDTOList;
}
