package com.project.pessoaapi.repository;


import com.project.pessoaapi.entity.PessoaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {

    Page<PessoaEntity> findByNomeContainingIgnoreCase(Pageable page, String nome);

    Page<PessoaEntity> findAll(Pageable pageable);

    Page<PessoaEntity> findByIdPessoa(Pageable pageable, Integer idPessoa);
}
