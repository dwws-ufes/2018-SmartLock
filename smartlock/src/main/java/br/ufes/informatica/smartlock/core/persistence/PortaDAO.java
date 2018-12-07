package br.ufes.informatica.smartlock.core.persistence;


import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.smartlock.core.domain.Porta;

@Local
public interface PortaDAO extends BaseDAO<Porta>{

	Porta retrieveByHouseId(int casaId);

	Porta retrieveByHouseAndDoorId(int casaId, int portaId);


}
