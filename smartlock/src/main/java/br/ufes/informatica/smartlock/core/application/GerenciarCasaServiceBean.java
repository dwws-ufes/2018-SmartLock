package br.ufes.informatica.smartlock.core.application;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudOperation;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.smartlock.core.domain.Casa;
import br.ufes.informatica.smartlock.core.domain.Porta;
import br.ufes.informatica.smartlock.core.persistence.CasaDAO;
import br.ufes.informatica.smartlock.core.persistence.PortaDAO;

@Stateless @PermitAll
public class GerenciarCasaServiceBean extends CrudServiceBean<Casa> implements GerenciarCasaService {

	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(CrudServiceBean.class.getCanonicalName());

	
	@EJB
	private AbrirPortaService abrirPortaService;
	
	@EJB
	private CasaDAO casaDAO;
	
	@EJB
	private PortaDAO portaDAO;
	
	@Override
	public BaseDAO<Casa> getDAO() {
		// TODO Auto-generated method stub
		return casaDAO;
	}
	
	
	@Override
	protected Casa validate(Casa newEntity, Casa oldEntity) {
		//logger.log(Level.FINE, "Validation not overridden by subclass. No need for validation.");
		if (getDAO().retrieveCount() == 0) {
			newEntity.setCasaId(1);
		}
		else {
			newEntity.setCasaId((int) (getDAO().retrieveCount()+1));
		}
		return newEntity;
	}
	
	protected Porta validatePorta(Porta newEntity, Porta oldEntity) {
		logger.log(Level.FINE, "Validation not overridden by subclass. No need for validation.");
		return newEntity;
	}
	
	protected void logPorta(CrudOperation operation, Porta entity) {
		logger.log(Level.FINE, "Logging (for operations over single entities) not overridden by subclass. No need for this type of logging.");
	}
	
	@Override
	public void create(Casa newCasa) {
		// Validates the entity before persisting.
		
		newCasa = validate(newCasa, null);

		List<Porta> lista = newCasa.criarPortas();
		
		for (int i = 0; i < newCasa.getNumPortas(); i++) {
			Porta porta = lista.get(i);
			porta = validatePorta(porta, null);

		// Save the entity.
			logPorta(CrudOperation.CREATE, porta);
			portaDAO.save(porta);
		}
		
		
		
		// Save the entity.
		log(CrudOperation.CREATE, newCasa);
		getDAO().save(newCasa);
		
	}


	@Override
	public void delete(Casa entity) {
		// Retrieves the real entity from the database.
		entity = getDAO().retrieveById(entity.getId());
		
		if (entity != null) {
			for (int i = 0; i < entity.getNumPortas(); i++) {
				Porta portas = portaDAO.retrieveByHouseId(entity.getCasaId());		
				// Deletes the entity.
				portaDAO.delete(portas);
				logPorta(CrudOperation.DELETE, portas);
			}
			// Deletes the entity.
			getDAO().delete(entity);
			log(CrudOperation.DELETE, entity);
		}
	}
	
	

	

	
		
	

}
