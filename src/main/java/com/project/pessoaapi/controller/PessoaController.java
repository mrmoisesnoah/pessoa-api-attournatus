package com.project.pessoaapi.controller;

import com.project.pessoaapi.controller.documentation.PessoaOpInterface;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaUpdateDTO;
import com.project.pessoaapi.enums.TipoEndereco;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.services.EnderecoService;
import com.project.pessoaapi.services.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoa")
public class PessoaController implements PessoaOpInterface {
    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;


    @GetMapping("/buscar-endereco")
    public List<EnderecoDTO> buscarEndereco(@RequestParam(required = false) String nome, @RequestParam TipoEndereco tipo) throws RegraDeNegocioException {
        return enderecoService.listarPorPessoaEtipoEndereco(nome,tipo);
    }

    @GetMapping("/listar-paginado")
    public ResponseEntity<PageDTO<PessoaDTO>> listarPaginado(@RequestParam(required = false) Integer idPessoa,
                                                   @RequestParam(required = false) String nome,
                                                   Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaService.listarPaginado(idPessoa, nome, pagina, tamanho), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        log.info("Cadastrando pessoa...");
        PessoaDTO p = pessoaService.create(pessoa);
        log.info("Pessoa cadastrada com sucesso!");
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable("idPessoa") Integer id,
                                            @Valid @RequestBody PessoaUpdateDTO pessoaUpdateDTO) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        PessoaDTO p = pessoaService.update(id, pessoaUpdateDTO);
        log.info("Pessoa atualizada com sucesso!");
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException {
        log.info("Pessoa removida com sucesso!");
        pessoaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
