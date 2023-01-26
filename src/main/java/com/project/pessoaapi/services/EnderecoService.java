package com.project.pessoaapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.EnderecoPessoaEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.entity.entitiesembedded.EnderecoPessoaID;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final ObjectMapper objectMapper;


    public EnderecoDTO create(Integer id, EnderecoCreateDTO enderecoCapturado) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = pessoaService.findById(id);
        EnderecoPessoaEntity enderecoPessoaEntity = new EnderecoPessoaEntity();

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);

        EnderecoEntity enderecoEntityTransform = objectMapper.convertValue(enderecoCapturado, EnderecoEntity.class);

        EnderecoEntity enderecoEntitySalvo = enderecoRepository.save(enderecoEntityTransform);

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntitySalvo, EnderecoDTO.class);

        enderecoPessoaEntity.setEnderecoPessoaPK(new EnderecoPessoaID());
        enderecoPessoaEntity.getEnderecoPessoaPK().setIdEndereco(enderecoDTO.getIdEndereco());
        enderecoPessoaEntity.getEnderecoPessoaPK().setIdPessoa(pessoaEntity.getIdPessoa());

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
        PessoaEntity pessoaEncontrada = pessoaService.findById(enderecoAtualizar.getIdPessoa());
        enderecoEntityRecuperado.getPessoaEntity().clear();
        enderecoEntityRecuperado.getPessoaEntity().add(pessoaEncontrada);

        EnderecoEntity enderecoEntityTransform = objectMapper.convertValue(enderecoAtualizar, EnderecoEntity.class);

        enderecoEntityRecuperado.setTipo(enderecoEntityTransform.getTipo());
        enderecoEntityRecuperado.setLogradouro(enderecoEntityTransform.getLogradouro());
        enderecoEntityRecuperado.setNumero(enderecoEntityTransform.getNumero());
        enderecoEntityRecuperado.setComplemento(enderecoEntityTransform.getComplemento());
        enderecoEntityRecuperado.setCep(enderecoEntityTransform.getCep());
        enderecoEntityRecuperado.setCidade(enderecoEntityTransform.getCidade());
        enderecoEntityRecuperado.setEstado(enderecoEntityTransform.getEstado());
        enderecoEntityRecuperado.setPais(enderecoEntityTransform.getPais());

        enderecoRepository.save(enderecoEntityRecuperado);

        PessoaDTO pessoaDTO =
                enderecoEntityRecuperado.getPessoaEntity()
                        .stream()
                        .map(pessoaEntity -> objectMapper.convertValue(pessoaEntity, PessoaDTO.class))
                        .findAny()
                        .orElseThrow();

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntityRecuperado, EnderecoDTO.class);

        enderecoDTO.setIdPessoa(pessoaDTO.getIdPessoa());

        return enderecoDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityEncontrado = findById(id);
        PessoaDTO pessoaDTO =
                enderecoEntityEncontrado.getPessoaEntity()
                        .stream()
                        .map(pessoaEntity -> objectMapper.convertValue(pessoaEntity, PessoaDTO.class))
                        .findAny()
                        .orElseThrow();
        enderecoRepository.delete(enderecoEntityEncontrado);
    }

    public List<EnderecoDTO> listarByPessoa(Integer id) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = pessoaService.findById(id);
        return pessoaEntity.getEnderecoEntity()
                .stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, EnderecoDTO.class))
                .toList();
    }

    public List<EnderecoDTO> listarByEndereco(Integer id) {
        return enderecoRepository.findById(id).stream()
                .map(enderecoEntity -> objectMapper.convertValue(enderecoEntity, EnderecoDTO.class))
                .toList();
    }

    public EnderecoEntity findById(Integer id) throws RegraDeNegocioException {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("O endereço não foi encontrado!"));
    }
}
