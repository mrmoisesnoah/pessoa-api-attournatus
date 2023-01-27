package com.project.pessoaapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.enums.TipoEndereco;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final ObjectMapper objectMapper;


    public EnderecoDTO create(Integer id, EnderecoCreateDTO enderecoCapturado) throws RegraDeNegocioException {
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        PessoaEntity pessoaEntity = pessoaService.findById(id);
        pessoaEntitySet.add(pessoaEntity);
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoEntity.setTipo(enderecoCapturado.getTipo());
        enderecoEntity.setLogradouro(enderecoCapturado.getLogradouro());
        enderecoEntity.setCep(enderecoCapturado.getCep());
        enderecoEntity.setNumero(enderecoCapturado.getNumero());
        enderecoEntity.setCidade(enderecoCapturado.getCidade());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);

        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntity);

        enderecoDTO.setNomePessoa(pessoaEntity.getNome());
        enderecoDTO.setTipo(enderecoSalvo.getTipo());
        enderecoDTO.setLogradouro(enderecoSalvo.getLogradouro());
        enderecoDTO.setCep(enderecoSalvo.getCep());
        enderecoDTO.setNumero(enderecoSalvo.getNumero());
        enderecoDTO.setCidade(enderecoSalvo.getCidade());

        return enderecoDTO;
    }

    public List<EnderecoDTO> list() {
        return enderecoRepository.findAll()
                .stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, EnderecoDTO.class))
                .toList();
    }

    public EnderecoDTO update(Integer id,
                              EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {

        EnderecoEntity enderecoEntityRecuperado = findById(id);

        enderecoEntityRecuperado.setTipo(enderecoAtualizar.getTipo());
        enderecoEntityRecuperado.setLogradouro(enderecoAtualizar.getLogradouro());
        enderecoEntityRecuperado.setNumero(enderecoAtualizar.getNumero());
        enderecoEntityRecuperado.setCep(enderecoAtualizar.getCep());
        enderecoEntityRecuperado.setCidade(enderecoAtualizar.getCidade());

        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntityRecuperado);

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoSalvo, EnderecoDTO.class);

        return enderecoDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityEncontrado = findById(id);
        enderecoRepository.delete(enderecoEntityEncontrado);
    }


    public EnderecoDTO listarPorPessoaEtipoEndereco(String nome, TipoEndereco tipo) throws RegraDeNegocioException {

        EnderecoEntity enderecoEncontrado = enderecoRepository.findByFiltro(nome, tipo)
                .orElseThrow(() -> new RegraDeNegocioException("O endereço não foi encontrado!"));
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEncontrado, EnderecoDTO.class);
        String nomePessoa = enderecoEncontrado.getPessoaEntity().stream().map(PessoaEntity::getNome).collect(Collectors.joining());
        enderecoDTO.setNomePessoa(nomePessoa);
        return enderecoDTO;
    }

    public EnderecoEntity findById(Integer id) throws RegraDeNegocioException {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("O endereço não foi encontrado!"));
    }
}
