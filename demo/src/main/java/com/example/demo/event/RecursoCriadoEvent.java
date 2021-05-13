package com.example.demo.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import com.example.demo.resource.CategoriaResource;
import com.example.demo.resource.PessoaResource;

/**
 * <p>Classe de eventos para
 * adicionar header Location</p>
 * 
 * @see PessoaResource método criar, buscar po Id
 * @see CategoriaResource método criar, listar, listar por Id
 * 
 * @since 12/05/2021 
 * @author Rafael Madakis
 * @version 1.0
 *
 */
public class RecursoCriadoEvent extends ApplicationEvent {

	
	private static final long serialVersionUID = 1L;
	
	
	private HttpServletResponse response;
	private Long codigo;

	/**
	 * @param source
	 */
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;		
		
	}
	
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	
	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}	
	

}
