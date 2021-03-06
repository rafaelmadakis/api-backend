package com.example.demo.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;

/**
 * <p>Classe para recurso de {@link Pessoa} com
 * métodos para criar e buscar pelo Id</p>
 * 
 * @since 12/05/2021
 * @author Rafael Madakis
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * <p>Método para criar registro de pessoa
	 * na entidade {@link Pessoa}</p>
	 * 
	 * @param pessoa
	 * @param response
	 * @return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva)
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));				
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	/**
	 * <p>Método para buscar pelo código na 
	 * entidade {@link Pessoa}</p>
	 * 
	 * @param codigo
	 * @return
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
	    return pessoa.isPresent() ? 
	            ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	/**
	 * <p>Método para remover pessoa da 
	 * entidade {@link Pessoa}</p>
	 * @param codigo
	 */
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		 Pessoa pessoa = new Pessoa();
	     pessoa.setCodigo(codigo);
	     this.pessoaRepository.delete(pessoa);
	}
	
	
	/**
	 * <p> Método para atualizar todos os dados 
	 * (não é atualização parcial)
	 *  de pessoa da entidade {@link Pessoa}</p>
	 *  
	 * @param codigo
	 * @param pessoa
	 * @return
	 */
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public Pessoa atualizar(@PathVariable Long codigo, @RequestBody Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
		      .orElseThrow(() -> new EmptyResultDataAccessException(1));

		  BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		  return this.pessoaRepository.save(pessoaSalva);
	}
	
}
