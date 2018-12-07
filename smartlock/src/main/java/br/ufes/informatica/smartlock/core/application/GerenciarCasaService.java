package br.ufes.informatica.smartlock.core.application;


import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smartlock.core.domain.Casa;

@Local
public interface GerenciarCasaService extends CrudService<Casa>{

	public void create(Casa newCasa);
}
