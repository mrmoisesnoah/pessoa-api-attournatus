package com.project.pessoaapi.controller;

import com.project.pessoaapi.dto.enderecodto.EnderecoPessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
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
public class PessoaController {
    private final PessoaService pessoaService;

    @GetMapping("/byname")
    public List<PessoaDTO> listByName(@RequestParam("nome") String nome) throws RegraDeNegocioException {
        return pessoaService.listByName(nome);
    }

    @GetMapping("/listar-endereco")
    public List<EnderecoPessoaDTO> listPessoaEndereco(@RequestParam(required = false, name = "idPessoa") Integer id)
            throws RegraDeNegocioException {
            return pessoaService.listPessoaEndereco(id);
    }




    @PostMapping
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        log.info("Cadastrando pessoa...");
        PessoaDTO p = pessoaService.create(pessoa);
        log.info("Pessoa cadastrada com sucesso!");
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    @GetMapping
    public List<PessoaDTO> list() {
        return pessoaService.list();
    }


    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable("idPessoa") Integer id,
                                            @Valid @RequestBody PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        PessoaDTO p = pessoaService.update(id, pessoaAtualizar);
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
