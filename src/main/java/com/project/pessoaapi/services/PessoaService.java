package com.project.pessoaapi.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaEnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaUpdateDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.repository.EnderecoRepository;
import com.project.pessoaapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    public PageDTO<PessoaDTO> listarPaginado(Integer idPessoa, String nome, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Pagina ou Tamanho não podem ser menores que zero.");
        }
        if (tamanho > 0) {
            Page<PessoaEntity> paginaRepository = filtrarPessoas(idPessoa, nome, pagina, tamanho);

            List<PessoaDTO> pessoaDTOList = paginaRepository.stream().map(this::converterParaDTO).toList();

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    pagina,
                    tamanho,
                    pessoaDTOList);
        }
        List<PessoaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        EnderecoCreateDTO enderecoCreateDTO = pessoaCreateDTO.getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);

        enderecoRepository.save(enderecoEntity);

        Set<EnderecoEntity> enderecoEntitySet = new HashSet<>();
        enderecoEntitySet.add(enderecoEntity);
        pessoaEntity.setEnderecoEntity(enderecoEntitySet);

        PessoaEntity pessoaEntitySalva = pessoaRepository.save(pessoaEntity);

        PessoaDTO pessoaDTO = converterParaDTO(pessoaEntitySalva);
        return pessoaDTO;
    }

    public PessoaDTO update(Integer id, PessoaUpdateDTO pessoaCapturada) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityRecuperada = findById(id);
        pessoaEntityRecuperada.setNome(pessoaCapturada.getNome());
        pessoaEntityRecuperada.setDataNascimento(pessoaCapturada.getDataNascimento());

        PessoaEntity pessoaSalva = pessoaRepository.save(pessoaEntityRecuperada);

        PessoaDTO pessoaDTO = converterParaDTO(pessoaSalva);
        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityEncontrada = findById(id);
        pessoaRepository.delete(pessoaEntityEncontrada);
    }

    public PessoaEntity findById(Integer id) throws RegraDeNegocioException {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Não foi posivel localizar essa pessoa!"));
    }

    public PessoaDTO converterParaDTO(PessoaEntity pessoaEntity) {
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
        List<PessoaEnderecoDTO> enderecoDTO = pessoaEntity.getEnderecoEntity().stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, PessoaEnderecoDTO.class))
                .toList();
        pessoaDTO.setEnderecoDTOList(enderecoDTO);
        return pessoaDTO;
    }


    public Page<PessoaEntity> filtrarPessoas(Integer idPessoa, String nome, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idPessoa == null)) {
            return pessoaRepository.findByIdPessoa(pageRequest, idPessoa);
        } else if (!(nome == null)) {
            return pessoaRepository.findByNomeContainingIgnoreCase(pageRequest, nome);
        }
        return pessoaRepository.findAll(pageRequest);
    }

}
