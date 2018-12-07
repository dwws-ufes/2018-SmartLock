package br.ufes.informatica.smartlock.core.persistence;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.smartlock.core.domain.Porta;

@Stateless
public class PortaJPADAO extends BaseJPADAO<Porta> implements PortaDAO{

	private static final long serialVersionUID = 1L;

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return entityManager;
	}

	@Override
	public Porta retrieveByHouseId(int casaId) {	
		for(Porta porta : retrieveAll()) {
			if(porta.getCasaId() == casaId) {
				
				return porta;
			}
		}
		
		return null;
}
	@Override
	public Porta retrieveByHouseAndDoorId(int casaId, int portaId) {	
		for(Porta porta : retrieveAll()) {
			if(porta.getCasaId() == casaId && porta.getIdPorta()== portaId) {
				
				return porta;
			}
		}
		
		return null;
}
	
}
