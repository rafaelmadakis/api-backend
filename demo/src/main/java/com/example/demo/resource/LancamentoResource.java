package com.example.demo.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.model.Lancamento;
import com.example.demo.repository.LancamentoRepository;
import com.example.demo.repository.filter.LancamentoFilter;

/**
 * <p>Classe para recurso de {@link Lancamento} com
 * métodos para listar e buscar pelo Id</p>
 * 
 * @Date 12/05/2021
 * @author Rafael Madakis
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	
	/**
	 * <p> Método para pesquisar lançamentos da 
	 *  entidade {@link Lancamento}</p>
	 *  
	 * @return lancamentoRepository.filtrar(lancamentoFilter);
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	
	/**
	 * <p> Método para buscar por Id na entidade
	 *  {@link Lancamento} </p>
	 *  
	 * @param codigo
	 * @return lancamento.isPresent() ? 
	            ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable @Valid Long codigo) {
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
	    return lancamento.isPresent() ? 
	            ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}
	
	
	/**
	 * <p> Método para criar lancamentos na 
	 * entidade {@link Lancamento}</p>
	 * 
	 * @param lancamento
	 * @param response
	 * @return
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	
	/**
	 * <p> Método para remover lancamento da 
	 * entidade {@link Lancamento} </p>
	 * @param codigo
	 */
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		 Lancamento lancamento = new Lancamento();
	     lancamento.setCodigo(codigo);
	     this.lancamentoRepository.delete(lancamento);
	     
	     
	}
	
	
	
	
}