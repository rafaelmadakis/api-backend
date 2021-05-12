package com.example.demo.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * <p> Classe para capturar e tratar erros 
 *  quando usuário tentar passar alguma 
 *  propriedade desconhecida </p>
 * 
 * @Date 12/05/2021
 * @author Rafael Madakis
 * @version 1.0
 * 
 **/
@ControllerAdvice
public class FinancaExceptionHandler  extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messsageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		 String mensagemUsuario = messsageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		 String mensagemDesenvolvedor = ex.getCause().toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	public static class Erro {
		
		private String mensagemUsuario;
		
		private String mensagemDesenvolvedor;

		/**
		 * @param mensagemUsuario
		 * @param mensagemDesenvolvedor
		 */
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}
		
		/**
		 * @return the mensagemUsuario
		 */
		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
		
		/**
		 * @return the mensagemDesenvolvedor
		 */
		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}

}
