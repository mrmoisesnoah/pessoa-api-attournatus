package com.project.pessoaapi.controller.documentation;


import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaUpdateDTO;
import com.project.pessoaapi.enums.TipoEndereco;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface PessoaOpInterface {

    @Operation(summary = "Busca por endereços de acordo com Tipo e nome do Titular.", description = "Listagem de dados a partir do Tipo de Endereço e nome da pessoa.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso! "),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping("/byname")
    List<EnderecoDTO> buscarEndereco(@RequestParam(required = false) String nome, @RequestParam("tipo") TipoEndereco tipo) throws RegraDeNegocioException;

    @Operation(summary = "Listagem de Pessoas paginada por ID e Nome.", description = "Lista os dados referentes a busca realizada através de nome ou ID, sendo estes opcionais")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    ResponseEntity<PageDTO<PessoaDTO>> listarPaginado(@RequestParam(required = false) Integer idPessoa,
                                                      @RequestParam(required = false) String nome,
                                                      Integer pagina, Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "Cadastro de Pessoa.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaCreateDTO pessoa) throws RegraDeNegocioException;

    @Operation(summary = "Edição e atualização de dados de Pessoa", description = "Edição e alteração de dados dos usuários por IdPessoa!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable("idPessoa") Integer id,
                                            @Valid @RequestBody PessoaUpdateDTO pessoaUpdateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Remoção de Pessoas.", description = "Remoção de dados dos usuários por IdPessoa!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados para busca!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idPessoa}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException;
}
