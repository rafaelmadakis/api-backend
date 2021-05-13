package com.example.demo.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *<p> Classe da entidade de Lancamento Filter</p>
 *
 * @Date 12/05/2021
 * @author Rafael Madakis
 *
 */
public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the dataVencimentoDe
	 */
	public LocalDate getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	/**
	 * @param dataVencimentoDe the dataVencimentoDe to set
	 */
	public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	/**
	 * @return the dataVencimentoAte
	 */
	public LocalDate getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	/**
	 * @param dataVencimentoAte the dataVencimentoAte to set
	 */
	public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}
	
	
	

}
