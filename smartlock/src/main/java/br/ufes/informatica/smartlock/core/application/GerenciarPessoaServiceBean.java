package br.ufes.informatica.smartlock.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smartlock.core.domain.Pessoa;
import br.ufes.informatica.smartlock.core.persistence.PessoaDAO;

@Stateless @PermitAll
public class GerenciarPessoaServiceBean extends CrudServiceBean<Pessoa> implements GerenciarPessoaService {

	private static final long serialVersionUID = 1L;

	@EJB
	private PessoaDAO pessoaDAO;
	
	@Override
	public BaseDAO<Pessoa> getDAO() {
		// TODO Auto-generated method stub
		return pessoaDAO;
	}

}
