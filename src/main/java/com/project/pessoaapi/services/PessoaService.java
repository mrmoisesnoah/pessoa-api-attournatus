package com.project.pessoaapi.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
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

    private final ObjectMapper objectMapper;
    private final EnderecoRepository enderecoRepository;


    public PessoaDTO create(PessoaCreateDTO pessoa) throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoa, PessoaEntity.class);
        EnderecoCreateDTO enderecoCreateDTO = pessoa.getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);

        enderecoRepository.save(enderecoEntity);

        Set<EnderecoEntity> enderecoEntitySet = new HashSet<>();
        enderecoEntitySet.add(enderecoEntity);
        pessoaEntity.setEnderecoEntity(enderecoEntitySet);

        PessoaEntity pessoaEntitySalva = pessoaRepository.save(pessoaEntity);

        List<PessoaEnderecoDTO> enderecoDTOSet = new ArrayList<>();
        PessoaEnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntity, PessoaEnderecoDTO.class);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntitySalva, PessoaDTO.class);

        enderecoDTOSet.add(enderecoDTO);
        pessoaDTO.setEnderecoDTOList(enderecoDTOSet);

        return pessoaDTO;
    }

    public PageDTO<PessoaDTO> listarPaginado(Integer page, Integer size) throws RegraDeNegocioException {
        if (size < 0 || page < 0) {
            throw new RegraDeNegocioException("Page ou Size não pode ser menor que zero.");
        }
        if (size > 0) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PessoaEntity> paginaRepository = pessoaRepository.findAll(pageRequest);

            List<PessoaDTO> pessoaDTOList = paginaRepository.stream().map(this::converterParaDTO).toList();

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    page,
                    size,
                    pessoaDTOList);
        }
        List<PessoaDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, listaVazia);
    }

    public PessoaDTO converterParaDTO(PessoaEntity pessoaEntity){
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
        List<PessoaEnderecoDTO> enderecoDTO = pessoaEntity.getEnderecoEntity().stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, PessoaEnderecoDTO.class))
                .toList();
        pessoaDTO.setEnderecoDTOList(enderecoDTO);
        return pessoaDTO;
    }

    public PessoaDTO update(Integer id, PessoaUpdateDTO pessoaCapturada) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityRecuperada = findById(id);

        PessoaEntity pessoaAtualizar = objectMapper.convertValue(pessoaCapturada, PessoaEntity.class);
        pessoaEntityRecuperada.setNome(pessoaAtualizar.getNome());
        pessoaEntityRecuperada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaRepository.save(pessoaEntityRecuperada);

        List<PessoaEnderecoDTO> pessoaEnderecoDTOList = pessoaEntityRecuperada.getEnderecoEntity().stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, PessoaEnderecoDTO.class)).toList();

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntityRecuperada, PessoaDTO.class);
        pessoaDTO.setEnderecoDTOList(pessoaEnderecoDTOList);
        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityEncontrada = findById(id);
        pessoaRepository.delete(pessoaEntityEncontrada);
    }

    public List<PessoaDTO> listByName(String nome) throws RegraDeNegocioException {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome)
                .stream().map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .toList();
    }

    public PessoaEntity findById(Integer id) throws RegraDeNegocioException {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Não foi posivel localizar essa pessoa!"));
    }
}
