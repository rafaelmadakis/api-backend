package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Pessoa;

/**
 * Interface de repositório para
 * a entidade {@link Pessoa}
 * @since 12/05/2021
 * @author Rafael Madakis
 *
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}