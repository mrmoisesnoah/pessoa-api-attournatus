package com.project.pessoaapi.controller.documentation;

import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface EnderecoOpInterface {
    @Operation(summary = "Listagem de Endereços paginados, por Id opcional, ", description = "Listagem de endereços a partir da pesquisa por IdEndereço, sendo este opcional, e o retorno paginado!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping("/{idEndereco}")
    ResponseEntity<PageDTO<EnderecoDTO>> listarPaginado(@RequestParam(required = false) Integer idEndereco,
                                                        Integer pagina, Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "Cadastro de Endereços.", description = "Cadastramento de endereços por IdPessoa!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping("/{idPessoa}")
    public ResponseEntity<EnderecoDTO> create(@PathVariable("idPessoa") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Edição e atualização de endereços", description = "Edição e alteração de endereço dos usuários a partir da idEndereço!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Remoção de endereços", description = "Remoção de endereços por IdEndereço!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados para busca!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idEndereco}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException;
}
