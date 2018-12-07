package br.ufes.informatica.smartlock.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smartlock.core.application.GerenciarPessoaService;
import br.ufes.informatica.smartlock.core.domain.Pessoa;

@Named @SessionScoped
public class GerenciarPessoaController extends CrudController<Pessoa> {

	private static final long serialVersionUID = 1L;

	@EJB
	private GerenciarPessoaService gerenciarPessoaService;
	
	@Override
	protected CrudService<Pessoa> getCrudService() {
		// TODO Auto-generated method stub
		return gerenciarPessoaService;
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		
	}

}
