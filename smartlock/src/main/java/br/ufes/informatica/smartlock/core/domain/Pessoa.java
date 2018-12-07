package br.ufes.informatica.smartlock.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Pessoa extends PersistentObjectSupport implements Comparable<Pessoa>{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	
	@NotNull
	@Size(max = 1)
	private char genero;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@NotNull
	private String email;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public int compareTo(Pessoa outra) {
		return this.nome.compareTo(outra.nome);
	}

}
