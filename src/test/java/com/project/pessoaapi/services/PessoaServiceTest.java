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
import com.project.pessoaapi.exceptions.RegraDeNegocioException;
import com.project.pessoaapi.factory.EnderecoFactory;
import com.project.pessoaapi.factory.PessoaFactory;
import com.project.pessoaapi.repository.EnderecoRepository;
import com.project.pessoaapi.repository.PessoaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {
    @InjectMocks
    private PessoaService pessoaService;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaRepository pessoaRepository;
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
        final String nome = "teste";

        List<PessoaDTO> listaVazia = new ArrayList<>();
        PageDTO<PessoaDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<PessoaDTO> paginaRecebida = pessoaService.listarPaginado(idPrograma, nome, numeroPagina, tamanho);

        assertEquals(paginaRecebida, alunoDTOPageDTOEsperado);

    }

    @Test
    public void deveListarPaginadoPorIdCorretamente() throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        final int id = pessoaEntity.getIdPessoa();
        final int numero = 0;
        final int tamanho = 3;
        PageImpl<PessoaEntity> listaPaginada = new PageImpl<>(List.of(pessoaEntity),
                PageRequest.of(numero, tamanho), 0);

        when(pessoaService.filtrarPessoas(id,null,numero,tamanho)).thenReturn(listaPaginada);
        PageDTO<PessoaDTO> pessoaDTOPageDTO =
                pessoaService.listarPaginado(id, null, numero, tamanho);

        assertNotNull(pessoaDTOPageDTO);
        assertEquals(1, pessoaDTOPageDTO.getTotalElementos());
        assertEquals(1, pessoaDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), pessoaDTOPageDTO.getPagina());
    }

    @Test
    public void deveListarPaginadoPorNomeCorretamente() throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        final String nome = pessoaEntity.getNome();
        final int numero = 0;
        final int tamanho = 3;
        PageImpl<PessoaEntity> listaPaginada = new PageImpl<>(List.of(pessoaEntity),
                PageRequest.of(numero, tamanho), 0);

        when(pessoaService.filtrarPessoas(null,nome,numero,tamanho)).thenReturn(listaPaginada);
        PageDTO<PessoaDTO> pessoaDTOPageDTO =
                pessoaService.listarPaginado(null, nome, numero, tamanho);

        assertNotNull(pessoaDTOPageDTO);
        assertEquals(1, pessoaDTOPageDTO.getTotalElementos());
        assertEquals(1, pessoaDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), pessoaDTOPageDTO.getPagina());
    }

    @Test
    public void deveListarPaginadoNomeEIdNulosCorretamente() throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        final int numero = 0;
        final int tamanho = 3;
        PageImpl<PessoaEntity> listaPaginada = new PageImpl<>(List.of(pessoaEntity),
                PageRequest.of(numero, tamanho), 0);

        when(pessoaService.filtrarPessoas(null,null,numero,tamanho)).thenReturn(listaPaginada);
        PageDTO<PessoaDTO> pessoaDTOPageDTO =
                pessoaService.listarPaginado(null, null, numero, tamanho);

        assertNotNull(pessoaDTOPageDTO);
        assertEquals(1, pessoaDTOPageDTO.getTotalElementos());
        assertEquals(1, pessoaDTOPageDTO.getQuantidadePaginas());
        assertEquals(listaPaginada.getPageable().getPageNumber(), pessoaDTOPageDTO.getPagina());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarListaPaginadoListaVaziaComExcessao() throws RegraDeNegocioException {
        final int numeroPagina = -1;
        final int tamanho = -1;
        final Integer idPrograma = 1;
        final String nome = "teste";

        List<PessoaDTO> listaVazia = new ArrayList<>();
        PageDTO<PessoaDTO> alunoDTOPageDTOEsperado = new PageDTO<>(0L, 0, 0, tamanho, listaVazia);

        PageDTO<PessoaDTO> paginaRecebida = pessoaService.listarPaginado(idPrograma, nome, numeroPagina, tamanho);

        assertEquals(paginaRecebida, alunoDTOPageDTOEsperado);

    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        PessoaCreateDTO pessoaCreateDTO = PessoaFactory.getPessoaCreateDTO();
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();

        when(pessoaRepository.save(any())).thenReturn(pessoaEntity);

        PessoaDTO pessoaDTO = pessoaService.create(pessoaCreateDTO);

        String logradouro = pessoaDTO.getEnderecoDTOList().stream()
                .map(PessoaEnderecoDTO::getLogradouro).collect(Collectors.joining());

        assertEquals(pessoaEntity.getNome(), pessoaDTO.getNome());
        assertEquals(pessoaEntity.getDataNascimento(), pessoaDTO.getDataNascimento());
        assertEquals(logradouro, enderecoEntity.getLogradouro());
        assertNotNull(pessoaDTO);
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {

        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        PessoaUpdateDTO pessoaUpdateDTO = PessoaFactory.getPessoaUpdateDTO();
        Integer id = 1;

        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoaEntity));
        when(pessoaRepository.save(any())).thenReturn(pessoaEntity);

        PessoaDTO pessoaDTO = pessoaService.update(id,pessoaUpdateDTO);

        assertEquals(pessoaEntity.getNome(), pessoaDTO.getNome());
        assertEquals(pessoaEntity.getDataNascimento(), pessoaDTO.getDataNascimento());
        assertNotNull(pessoaDTO);
    }

    @Test
    public void deveTestarDeletarPorIdComSucesso() throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        Integer idPessoa = 1;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        pessoaService.delete(idPessoa);

    }

    @Test
    public void deveTestarFindPorIdComSucesso() throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = PessoaFactory.getPessoaEntity();
        Integer idPessoa = 1;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        PessoaEntity pessoa = pessoaService.findById(idPessoa);

        assertNotNull(pessoa);
    }
}
