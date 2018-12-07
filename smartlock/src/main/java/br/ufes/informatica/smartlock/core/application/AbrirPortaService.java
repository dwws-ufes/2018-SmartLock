package br.ufes.informatica.smartlock.core.application;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import br.ufes.informatica.smartlock.core.domain.Casa;
import br.ufes.informatica.smartlock.core.domain.Porta;

@Local
public interface AbrirPortaService extends Serializable{
	
	public List<Casa> retrieveAllHouses();

	
	public List<Porta> retrieveAllDoors();

	
	public List<Porta> filterByHouse(List<Porta> portas, Casa casa);

	public void alterarStatusPortas(int casaId, int portaId);


}
