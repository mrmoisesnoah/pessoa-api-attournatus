package com.project.pessoaapi.repository;


import com.project.pessoaapi.entity.EnderecoEntity;
import com.project.pessoaapi.enums.TipoEndereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

    Page<EnderecoEntity> findByIdEndereco(Pageable page, Integer id);

    Page<EnderecoEntity> findAll(Pageable page);

    List<EnderecoEntity> findAllByTipo(TipoEndereco tipoEndereco);

    @Query("SELECT distinct ed " +
            "FROM ENDERECO ed " +
            "INNER JOIN ed.pessoaEntity pessoa " +
            "ON (ed.tipo = :tipo)" +
            "AND ( :nome is null or UPPER(pessoa.nome) LIKE UPPER(concat('%',:nome, '%')))")
    List<EnderecoEntity> findByFiltro(String nome, TipoEndereco tipo);


}
