package com.project.pessoaapi.repository;


import com.project.pessoaapi.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {

    List<PessoaEntity> findByNomeContainingIgnoreCase(String nome);
    List<PessoaEntity> findByCpf(String cpf);
    List<PessoaEntity> findByEmailContaining(String email);
    List<PessoaEntity> findByDataNascimentoBetween(LocalDate inicial, LocalDate fim);




    List<PessoaEntity> findAll();
}
