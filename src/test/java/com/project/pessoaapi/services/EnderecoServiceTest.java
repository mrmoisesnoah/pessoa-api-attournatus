package com.project.pessoaapi.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.paginacaodto.PageDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaCreateDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaEnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaUpdateDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.enums.TipoEndereco;
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.factory.EnderecoFactory;
import com.project.pessoaapi.factory.PessoaFactory;
import com.project.pessoaapi.repository.EnderecoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {
    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaService pessoaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(pessoaService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarListaPaginadoListaVaziaComSucesso() throws RegraDeNegocioException {
        final int numeroPagina = 0;
        final int tamanho = 0;
        final Integer idPrograma = 1;

        List<EnderecoDTO> listaVazia = new ArrayList<>();
        PageDTO<EnderecoDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<EnderecoDTO> paginaRecebida = enderecoService.listarPaginado(idPrograma, numeroPagina, tamanho);

        assertEquals(paginaRecebida, alunoDTOPageDTOEsperado);
    }

    @Test
    public void deveListarPaginadoPorIdCorretamente() throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        final int id = enderecoEntity.getIdEndereco();
        final int numero = 0;
        final int tamanho = 3;
        PageImpl<EnderecoEntity> listaPaginada = new PageImpl<>(List.of(enderecoEntity),
                PageRequest.of(numero, tamanho), 0);

        when(enderecoService.filtrarEnderecos(id,numero,tamanho)).thenReturn(listaPaginada);
        PageDTO<EnderecoDTO> enderecoDTOPageDTO =
                enderecoService.listarPaginado(id,numero, tamanho);

        assertNotNull(enderecoDTOPageDTO);
        assertEquals(1, enderecoDTOPageDTO.getTotalElementos());
        assertEquals(1, enderecoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), enderecoDTOPageDTO.getPagina());
    }

    @Test
    public void deveListarPaginadoComIdNuloCorretamente() throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        final int numero = 0;
        final int tamanho = 3;
        PageImpl<EnderecoEntity> listaPaginada = new PageImpl<>(List.of(enderecoEntity),
                PageRequest.of(numero, tamanho), 0);

        when(enderecoService.filtrarEnderecos(null,numero,tamanho)).thenReturn(listaPaginada);
        PageDTO<EnderecoDTO> enderecoDTOPageDTO =
                enderecoService.listarPaginado(null,numero, tamanho);

        assertNotNull(enderecoDTOPageDTO);
        assertEquals(1, enderecoDTOPageDTO.getTotalElementos());
        assertEquals(1, enderecoDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), enderecoDTOPageDTO.getPagina());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarListaPaginadoListaVaziaComExcessao() throws RegraDeNegocioException {
        final int numeroPagina = -1;
        final int tamanho = -1;
        final Integer idPrograma = 1;

        List<EnderecoDTO> listaVazia = new ArrayList<>();
        PageDTO<EnderecoDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<EnderecoDTO> paginaRecebida = enderecoService.listarPaginado(idPrograma, numeroPagina, tamanho);

        assertEquals(paginaRecebida, alunoDTOPageDTOEsperado);
    }

    @Test
    public void deveTestarlistarPorPessoaEtipoEnderecoComSucesso() throws RegraDeNegocioException {

        List<EnderecoEntity> enderecoEntityList = new ArrayList<>();
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        enderecoEntityList.add(enderecoEntity);


        when(enderecoRepository.findAllByTipo(any())).thenReturn(enderecoEntityList);
        List<EnderecoDTO> enderecoDTOList =
                enderecoService.listarPorPessoaEtipoEndereco(null, TipoEndereco.PRINCIPAL);

        assertNotNull(enderecoDTOList);
    }

    @Test
    public void deveTestarlistarPorPessoaEtipoEnderecoPorNomeComSucesso() throws RegraDeNegocioException {

        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        String nome = PessoaFactory.getPessoaEntity().getNome();

        when(enderecoRepository.findByFiltro(any(),any())).thenReturn(Optional.of(enderecoEntity));
        List<EnderecoDTO> enderecoDTOList =
                enderecoService.listarPorPessoaEtipoEndereco(nome, TipoEndereco.PRINCIPAL);

        assertNotNull(enderecoDTOList);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        EnderecoCreateDTO enderecoCreateDTO = EnderecoFactory.getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        Integer idPessoa = pessoaEntity.getIdPessoa();


        when(pessoaService.findById(any())).thenReturn(pessoaEntity);
        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        EnderecoDTO enderecoDTO = enderecoService.create(idPessoa,enderecoCreateDTO);


        assertEquals(enderecoEntity.getIdEndereco(), enderecoDTO.getIdEndereco());
        assertEquals(enderecoEntity.getLogradouro(), enderecoDTO.getLogradouro());
        assertNotNull(enderecoDTO);
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        EnderecoCreateDTO enderecoCreateDTO = EnderecoFactory.getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Set<PessoaEntity> pessoaEntitySet = new HashSet<>();
        pessoaEntitySet.add(PessoaFactory.getPessoaEntity());
        enderecoEntity.setPessoaEntity(pessoaEntitySet);
        Integer idEndereco = pessoaEntity.getIdPessoa();

        when(enderecoRepository.findById(any())).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        EnderecoDTO enderecoDTO = enderecoService.update(idEndereco,enderecoCreateDTO);

        assertEquals(enderecoEntity.getIdEndereco(), enderecoDTO.getIdEndereco());
        assertEquals(enderecoEntity.getLogradouro(), enderecoDTO.getLogradouro());
        assertNotNull(enderecoDTO);
    }

    @Test
    public void deveTestarDeletarPorIdComSucesso() throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Integer idEndereco = 1;
        when(enderecoRepository.findById(idEndereco)).thenReturn(Optional.of(enderecoEntity));
        enderecoService.delete(idEndereco);

    }

    @Test
    public void deveTestarFindPorIdComSucesso() throws RegraDeNegocioException {
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        Integer idEndereco = 1;
        when(enderecoRepository.findById(idEndereco)).thenReturn(Optional.of(enderecoEntity));
        EnderecoEntity endereco = enderecoService.findById(idEndereco);

        assertNotNull(endereco);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindPorIdComExcessao() throws RegraDeNegocioException {
        Integer idEndereco = 100;
        enderecoService.findById(idEndereco);

    }
}
