package br.ufes.informatica.smartlock.core.persistence;


import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smartlock.core.domain.Pessoa;

@Local
public interface PessoaDAO extends BaseDAO<Pessoa>{

}
