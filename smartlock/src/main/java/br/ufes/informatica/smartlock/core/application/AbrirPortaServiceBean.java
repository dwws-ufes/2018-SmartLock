package br.ufes.informatica.smartlock.core.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.informatica.smartlock.core.domain.Porta;
import br.ufes.informatica.smartlock.core.domain.Casa;
import br.ufes.informatica.smartlock.core.persistence.CasaDAO;
import br.ufes.informatica.smartlock.core.persistence.PortaDAO;

@Stateless
@PermitAll
public class AbrirPortaServiceBean implements AbrirPortaService{
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AbrirPortaServiceBean.class.getCanonicalName());

	
	@EJB
	private CasaDAO casaDAO;
	
	@EJB
	private PortaDAO portaDAO;
	
	public List<Casa> retrieveAllHouses(){
		
		return casaDAO.retrieveAll();
	}
	
	public List<Porta> retrieveAllDoors(){
		
		return portaDAO.retrieveAll();
	}
	
	public List<Porta> filterByHouse(List<Porta> portas, Casa casa){
		
		portas = portas.stream().filter(porta -> porta.getCasaId() == casa.getCasaId()).collect(Collectors.toList());
		
		return portas;
	}
	
	
	public void alterarStatusPortas(int casaId, int portaId){
		
		Porta porta = portaDAO.retrieveByHouseAndDoorId(casaId, portaId);
		
		if (porta.getisActive()==false) {
			porta.setisActive(true);
			portaDAO.save(porta);
			logger.log(Level.INFO, "Door changed status from closed to open!");

		}
			
		else {
			porta.setisActive(false);
			portaDAO.save(porta);
			logger.log(Level.INFO, "Door changed status from open to closed!");

		}
	}
	
}	
