package br.ufes.informatica.smartlock.core.controller;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.smartlock.core.application.AbrirPortaService;
import br.ufes.informatica.smartlock.core.domain.Casa;
import br.ufes.informatica.smartlock.core.domain.Porta;

@Named @SessionScoped
public class AbrirPortaController extends JSFController {


	private static final long serialVersionUID = 1L;
	
	//@Inject
	//private GerenciarCasaController gerenciarCasaController;
	
	private static final Logger logger = Logger.getLogger(AbrirPortaController.class.getCanonicalName());

	
	@EJB
	private AbrirPortaService abrirPortaService;
	
	private List<Porta> portas;
	
	private Porta portaSelecionada;
	
	private Casa casaSelecionada;
	
	private List<Casa> casas;
	
	@PostConstruct
	public void init() {
		
		portas = abrirPortaService.retrieveAllDoors();
	}
	
	
	public void filtrarCasa() {
		
		portas = abrirPortaService.retrieveAllDoors();
		
		if(casaSelecionada != null) {
			portas = abrirPortaService.filterByHouse(portas, casaSelecionada);
		}
		
	}
	
	public void abrirPorta() {
		
			
		if(casaSelecionada.getCasaId() == portaSelecionada.getCasaId())
			abrirPortaService.alterarStatusPortas(casaSelecionada.getCasaId(), portaSelecionada.getIdPorta());

		else
			logger.log(Level.INFO, "Error");

		
	}
	
	public String alterarPorta() {
		
		logger.log(Level.INFO, "Beginning conversation...");
		
		return "/core/abrirPorta/" + "index.xhtml";
	}
			

	public List<Porta> getPortas() {
		return portas;
	}


	public void setPortas(List<Porta> portas) {
		this.portas = portas;
	}


	public Porta getPortaSelecionada() {
		return portaSelecionada;
	}


	public void setPortaSelecionada(Porta portaSelecionada) {
		this.portaSelecionada = portaSelecionada;
	}


	public Casa getCasaSelecionada() {
		return casaSelecionada;
	}


	public void setCasaSelecionada(Casa casaSelecionada) {
		this.casaSelecionada = casaSelecionada;
	}


	public List<Casa> getCasas(){
		casas = abrirPortaService.retrieveAllHouses();
		return casas;
	}
}
