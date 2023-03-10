package com.project.pessoaapi.services;

import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.enums.TipoEndereco;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;

    public PageDTO<EnderecoDTO> listarPaginado(Integer idPessoa, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        if (tamanho < 0 || pagina < 0) {
            throw new RegraDeNegocioException("Pagina e Tamanho não podem ser menores que zero.");
        }
        if (tamanho > 0) {
            Page<EnderecoEntity> paginaRepository = filtrarEnderecos(idPessoa,pagina,tamanho);

            List<EnderecoDTO> pessoaDTOList = paginaRepository.stream().map(this::converterParaDTO).toList();

            return new PageDTO<>(paginaRepository.getTotalElements(),
                    paginaRepository.getTotalPages(),
                    pagina,
                    tamanho,
                    pessoaDTOList);
        }
        List<EnderecoDTO> listaVazia = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, tamanho, listaVazia);
    }

    public  List<EnderecoDTO> listarPorPessoaEtipoEndereco(String nome, TipoEndereco tipo) throws RegraDeNegocioException {
        if(!(nome == null)) {
            return enderecoRepository.findByFiltro(nome, tipo)
                    .stream().map(this::converterParaDTO).toList();
        }
        return enderecoRepository.findAllByTipo(tipo).stream()
                .map(this::converterParaDTO).toList();
    }

    public EnderecoDTO create(Integer id, EnderecoCreateDTO enderecoCapturado) throws RegraDeNegocioException {
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        PessoaEntity pessoaEntity = pessoaService.findById(id);
        pessoaEntitySet.add(pessoaEntity);
        EnderecoEntity enderecoEntity = new EnderecoEntity();

        enderecoEntity.setTipo(enderecoCapturado.getTipo());
        enderecoEntity.setLogradouro(enderecoCapturado.getLogradouro());
        enderecoEntity.setCep(enderecoCapturado.getCep());
        enderecoEntity.setNumero(enderecoCapturado.getNumero());
        enderecoEntity.setCidade(enderecoCapturado.getCidade());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);

        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntity);
        EnderecoDTO enderecoDTO = converterParaDTO(enderecoSalvo);
        return enderecoDTO;
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

        EnderecoDTO enderecoDTO = converterParaDTO(enderecoSalvo);

        return enderecoDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityEncontrado = findById(id);
        enderecoRepository.delete(enderecoEntityEncontrado);
    }

    public EnderecoEntity findById(Integer id) throws RegraDeNegocioException {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("O endereço não foi encontrado!"));
    }

    public EnderecoDTO converterParaDTO(EnderecoEntity enderecoEntity){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        String nomePessoa = enderecoEntity.getPessoaEntity().stream()
                .map(PessoaEntity::getNome).collect(Collectors.joining());
        enderecoDTO.setNomePessoa(nomePessoa);
        enderecoDTO.setIdEndereco(enderecoEntity.getIdEndereco());
        enderecoDTO.setTipo(enderecoEntity.getTipo());
        enderecoDTO.setLogradouro(enderecoEntity.getLogradouro());
        enderecoDTO.setCep(enderecoEntity.getCep());
        enderecoDTO.setNumero(enderecoEntity.getNumero());
        enderecoDTO.setCidade(enderecoEntity.getCidade());
        return enderecoDTO;
    }

    public Page<EnderecoEntity> filtrarEnderecos(Integer idEndereco, Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        if (!(idEndereco == null)) {
            return enderecoRepository.findByIdEndereco(pageRequest, idEndereco);
        }
        return enderecoRepository.findAll(pageRequest);
    }
}
