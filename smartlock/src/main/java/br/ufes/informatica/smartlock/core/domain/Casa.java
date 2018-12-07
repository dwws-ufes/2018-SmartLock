package br.ufes.informatica.smartlock.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Casa extends PersistentObjectSupport implements Comparable<Casa>{
	
	private static final long serialVersionUID = 1L;
	
	private int numMoradores = 0;
	
	@NotNull
	private int numPortas;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "casa")
	private List<Porta> portas;
	
	@NotNull
	public int casaId;
	
	@NotNull 
	@Size(max = 50)
	private String endereco;
	
	@NotNull @Size(max = 40)
	private String cidade;
	
	@NotNull @Size(max = 100)
	private String estado;
	
	//private Estado estados;
	
	@NotNull @Size(max = 20)
	private String pais;

	public int getNumPortas() {
		return numPortas;
	}

	public void setNumPortas(int numPortas) {
		this.numPortas = numPortas;
	}

	public List<Porta> getPortas() {
		return portas;
	}

	public void setPortas(List<Porta> portas) {
		this.portas = portas;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}


	
	
	public int getCasaId() {
		return casaId;
	}

	public void setCasaId(int casaId) {
		this.casaId = casaId;
	}

	@Override
	public int compareTo(Casa o) {
		
		return numPortas - o.numPortas;
	}

	@Override
	public String toString()
	{
	    return "Casa ID:"+ this.casaId;
	}
	
	public List<Porta> criarPortas() {
		
		portas = new ArrayList<Porta>();
		for (int i = 0; i < numPortas; i++) {
			Porta p = new Porta();
			p.setIdPorta(i+1);
			p.setisActive(false);	
			p.setCasaId(casaId);
			portas.add(i,p);
		}
		return portas;
		
	}
	
}
