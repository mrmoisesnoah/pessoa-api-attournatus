package com.project.pessoaapi.dto.pessoadto;

import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class PessoaUpdateDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Nome da Pessoa. Campo não pode ser vazio.", example = "Moises Noah")
    private String nome;
    @Past
    @NotNull
    @Schema(description = "Data de Nascimento do Usuario. Campo não pode ser vazio.", example = "1999/09/09")
    private LocalDate dataNascimento;

}
