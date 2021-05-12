package com.example.demo.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.model.Lancamento;
import com.example.demo.repository.LancamentoRepository;

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
	 * <p> Método para listar lançamentos da 
	 *  entidade {@link Lancamento}</p>
	 *  
	 * @return lancamentoRepository.findAll()
	 */
	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
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
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
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
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	
}