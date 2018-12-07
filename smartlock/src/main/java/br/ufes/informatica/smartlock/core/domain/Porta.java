package br.ufes.informatica.smartlock.core.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Porta extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private int idPorta;
	
	
	private boolean isActive;

	@NotNull
	public int casaId;
	
	@ManyToOne
	private Casa casa;
	
	public int getIdPorta() {
		return idPorta;
	}

	public void setIdPorta(int idPorta) {
		this.idPorta = idPorta;
	}

	public boolean getisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getCasaId() {
		return casaId;
	}

	public void setCasaId(int casaId) {
		this.casaId = casaId;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	@Override
	public String toString()
	{
		if(isActive == false)
			return "Fechada";
		else
			return "Aberta";
	    //return this.endereco;
	}
	

}
