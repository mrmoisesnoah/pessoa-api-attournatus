package com.project.pessoaapi.factory;

import com.project.pessoaapi.dto.enderecodto.EnderecoCreateDTO;
import com.project.pessoaapi.dto.enderecodto.EnderecoDTO;
import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.enums.TipoEndereco;

public class EnderecoFactory {

    public static EnderecoEntity getEnderecoEntity(){
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setIdEndereco(1);
        enderecoEntity.setLogradouro("Rua teste");
        enderecoEntity.setCep("72210050");
        enderecoEntity.setTipo(TipoEndereco.PRINCIPAL);
        enderecoEntity.setCidade("Ceilandia");
        enderecoEntity.setNumero(1);
        return enderecoEntity;
    }

    public static EnderecoDTO getEnderecoDTO(){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(1);
        enderecoDTO.setLogradouro("Rua teste");
        enderecoDTO.setCep("72210050");
        enderecoDTO.setTipo(TipoEndereco.PRINCIPAL);
        enderecoDTO.setCidade("Ceilandia");
        enderecoDTO.setNumero(1);
        return enderecoDTO;
    }

    public static EnderecoCreateDTO getEnderecoCreateDTO(){
        EnderecoCreateDTO enderecoCreateDTO = new EnderecoCreateDTO();
        enderecoCreateDTO.setLogradouro("Rua teste");
        enderecoCreateDTO.setCep("72210050");
        enderecoCreateDTO.setTipo(TipoEndereco.PRINCIPAL);
        enderecoCreateDTO.setCidade("Ceilandia");
        enderecoCreateDTO.setNumero(1);
        return enderecoCreateDTO;
    }

}
