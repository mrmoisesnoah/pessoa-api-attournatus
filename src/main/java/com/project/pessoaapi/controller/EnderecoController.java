package com.project.pessoaapi.controller;


import com.project.pessoaapi.controller.documentation.EnderecoOpInterface;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class EnderecoController implements EnderecoOpInterface {
    private final EnderecoService enderecoService;

    @GetMapping("/listar-paginado")
    public ResponseEntity<PageDTO<EnderecoDTO>> listarPaginado(@RequestParam(required = false) Integer idEndereco,
                                                               Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listarPaginado(idEndereco, pagina, tamanho), HttpStatus.OK);
    }

    @PostMapping("/{idPessoa}")
    public ResponseEntity<EnderecoDTO> create(@PathVariable("idPessoa") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Gerando endereço...");
        log.info("Vinculando a Pessoa...");
        EnderecoDTO e = enderecoService.create(id, enderecoCreateDTO);
        log.info("Endereço criado!");
        return new ResponseEntity<>(e, HttpStatus.OK);
    }


    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando endereço...");
        EnderecoDTO e = enderecoService.update(id, enderecoAtualizar);
        log.info("Endereço Atualizado!");
        return new ResponseEntity<>(e, HttpStatus.OK);
    }


    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException {
        log.info("Endereço removido!");
        enderecoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
