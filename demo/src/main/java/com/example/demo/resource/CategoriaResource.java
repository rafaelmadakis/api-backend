package com.example.demo.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;

/**
 * <p> Classe da resource Categoria com métodos Listar, 
 * pesquisar por id, inserir e deletar registros
 *  na tabela de categorias </p>
 * 
 * @since 12/05/2021
 * @author Rafael Madakis
 * @version 1.0
 * 
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * <p>
	 * Método para listar todos os registros da tabela de categorias </p>
	 * 
	 * @return categoriaRepository.findAll()
	 */	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	/**
	 * <p>
	 * Método para criar novos registros na table de categorias
	 * </p>
	 * 
	 * @param categoria
	 * @param response
	 * @return ResponseEntity.created(uri).body(categoriaSalva)
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')  and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));	
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

	}

	/**
	 * <p>
	 * Método para buscar por código determinado registro na tabela de categorias
	 * </p>
	 * 
	 * @param codigo
	 * @return categoria.isPresent() ? 
	            ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
	    Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	    return categoria.isPresent() ? 
	            ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}

}