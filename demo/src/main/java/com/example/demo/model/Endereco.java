package com.example.demo.model;

import javax.persistence.Embeddable;


/**
 * Entidade endereço(embeddable)
 * @since 12/05/2021
 * @author Rafael Madakis
 * Date: 12/05/2021
 * @version 1.0
 *
 */
@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;
	
	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}
	
	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}
	
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}
	
	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}
	
	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}
	
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	
	
}
