package com.project.pessoaapi.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoPessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    private final ObjectMapper objectMapper;


    public PessoaDTO create(PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoa, PessoaEntity.class);
        PessoaEntity pessoaEntitySalva = pessoaRepository.save(pessoaEntity);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntitySalva, PessoaDTO.class);
        return pessoaDTO;
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll()
                .stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class)).toList();
    }
    public EnderecoPessoaDTO getEnderecoPessoaDTO(PessoaEntity pessoaEntity){
        EnderecoPessoaDTO enderecoPessoaDTO = objectMapper.convertValue(pessoaEntity, EnderecoPessoaDTO.class);
        enderecoPessoaDTO.setEnderecoDTOS(pessoaEntity.getEnderecoEntity()
                .stream()
                .map(enderecoEntity ->objectMapper.convertValue(enderecoEntity, EnderecoDTO.class))
                .collect(Collectors.toList()));
        return enderecoPessoaDTO;
    }




    public List<EnderecoPessoaDTO> listPessoaEndereco(Integer id){
        if(id != null) {
            return pessoaRepository.findById(id)
                    .stream()
                    .map(this::getEnderecoPessoaDTO)
                    .toList();
        } else {
            return pessoaRepository.findAll()
                    .stream()
                    .map(this::getEnderecoPessoaDTO)
                    .toList();
        }
    }


    public PessoaDTO update(Integer id, PessoaCreateDTO pessoaCapturada) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityRecuperada = findById(id);

        PessoaEntity pessoaAtualizar = objectMapper.convertValue(pessoaCapturada, PessoaEntity.class);
        pessoaEntityRecuperada.setCpf(pessoaAtualizar.getCpf());
        pessoaEntityRecuperada.setNome(pessoaAtualizar.getNome());
        pessoaEntityRecuperada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaRepository.save(pessoaEntityRecuperada);

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntityRecuperada, PessoaDTO.class);
        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        PessoaEntity pessoaEntityEncontrada = findById(id);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntityEncontrada, PessoaDTO.class);
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
