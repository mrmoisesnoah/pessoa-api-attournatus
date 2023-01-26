package com.project.pessoaapi.dto.pessoadto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PessoaCreateDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Nome da Pessoa. Campo não pode ser vazio.", example = "Moises Noah")
    private String nome;
    @Past
    @NotNull
    @Schema(description = "Data de Nascimento do Usuario. Campo não pode ser vazio.", example = "1999/09/09")
    private LocalDate dataNascimento;
    @CPF
    @Schema(description = "CPF do Usuário. Minimo de 11 digitos. Campo não pode ser vazio.", example = "734.364.050-00")
    private String cpf;
    @NotNull
    @Schema(description = "Email do usuário. Campo não pode ser vazio.", example = "mrmoisesnoah@gmail.com")
    private String email;
}
