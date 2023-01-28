package com.project.pessoaapi.factory;

import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaEnderecoDTO;
import com.project.pessoaapi.dto.pessoadto.PessoaUpdateDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.entity.PessoaEntity;
import com.project.pessoaapi.enums.TipoEndereco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PessoaFactory {

    public static PessoaEntity getPessoaEntity(){
        PessoaEntity pessoaEntity = new PessoaEntity();
        Set<EnderecoEntity> enderecoEntitySet = new HashSet<>();
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntitySet.add(enderecoEntity);
        pessoaEntity.setIdPessoa(1);
        pessoaEntity.setNome("Joao Teste");
        pessoaEntity.setDataNascimento(LocalDate.of(1994, 12, 01));
        pessoaEntity.setEnderecoEntity(enderecoEntitySet);
        return pessoaEntity;
    }
    public static PessoaDTO getPessoaDTO(){
        PessoaDTO pessoaDTO = new PessoaDTO();
        List<PessoaEnderecoDTO> pessoaEnderecoDTOList = new ArrayList<>();
        pessoaEnderecoDTOList.add(getPessoaEnderecoDTO());
        pessoaDTO.setIdPessoa(1);
        pessoaDTO.setNome("Joao Teste");
        pessoaDTO.setDataNascimento(LocalDate.of(1994, 12, 01));
        pessoaDTO.setEnderecoDTOList(pessoaEnderecoDTOList);
        return pessoaDTO;
    }

    public static PessoaUpdateDTO getPessoaUpdateDTO(){
        PessoaUpdateDTO pessoaUpdateDTO = new PessoaUpdateDTO();
        pessoaUpdateDTO.setNome("Joao Teste");
        pessoaUpdateDTO.setDataNascimento(LocalDate.of(1994, 12, 01));
        return pessoaUpdateDTO;
    }
    public static PessoaEnderecoDTO getPessoaEnderecoDTO(){
        PessoaEnderecoDTO pessoaEnderecoDTO = new PessoaEnderecoDTO();
        pessoaEnderecoDTO.setIdEndereco(1);
        pessoaEnderecoDTO.setCep("72210050");
        pessoaEnderecoDTO.setTipo(TipoEndereco.PRINCIPAL);
        pessoaEnderecoDTO.setCidade("Ceilandia");
        pessoaEnderecoDTO.setLogradouro("Rua Samambaia");
        return pessoaEnderecoDTO;
    }
}
