package com.project.pessoaapi.repository;


import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.enums.TipoEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

    List<EnderecoEntity> findByTipo(TipoEndereco tipoEndereco);
    List<EnderecoEntity> findByCepOrderByLogradouro(String cep);

    @Query("select e from  ENDERECO_PESSOA e WHERE e.pais = :pais")
    List<EnderecoEntity>findByPais(@Param("pais") String pais);

    @Query("select e from  ENDERECO_PESSOA e join e.pessoaEntity p where p.idPessoa = :idPessoa")
    List<EnderecoEntity>findByIdPessoa(@Param("idPessoa") Integer idPessoa);

}
