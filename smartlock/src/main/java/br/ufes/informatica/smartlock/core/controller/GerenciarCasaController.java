package br.ufes.informatica.smartlock.core.controller;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smartlock.core.application.GerenciarCasaService;
import br.ufes.informatica.smartlock.core.domain.Casa;
import br.ufes.informatica.smartlock.core.domain.Porta;

@Named @SessionScoped
public class GerenciarCasaController extends CrudController<Casa> {


	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(GerenciarCasaController.class.getCanonicalName());

	
	@EJB
	private GerenciarCasaService gerenciarCasaService;
	

	
	private Casa estaCasa;
	
	//private Estado estado;
	
	//private List<Estado> estados;
	
	private List<String> listaEstadosNaoTratada;
	
	private List<String> listaEstados;
	
	private List<String> listaCidades;
		
	private String nomeEstado;
	
	public Casa getEstaCasa() {
		return estaCasa;
	}




	@Override
	protected CrudService<Casa> getCrudService() {
		// TODO Auto-generated method stub
		return gerenciarCasaService;
	}

	
	@Override
	public String retrieve(Long id) {
		
		readOnly = true;
		
		if (selectedEntity == null) retrieveExistingEntity(id);
		else {
			// Asks the CRUD service to fetch any lazy collection that possibly exists.
			selectedEntity = getCrudService().fetchLazy(selectedEntity);
			checkSelectedEntity();
		}

		// Goes to the form.
		return getViewPath() + "form.xhtml?faces-redirect=" + getFacesRedirect();
	}
	
	@Override
	public String list() {

		// Clears the selection.
		selectedEntity = null;

		// Gets the entity count.
		count();

		// Checks if the index of the listing should be changed and reload the page.
		if (firstEntityIndex < 0) goFirst();
		else if (lastEntityIndex > entityCount) goLast();
		else retrieveEntities();

		// Goes to the listing.
		return getViewPath() + "index.xhtml";
}

	public String alterarPorta() {
		
		logger.log(Level.INFO, "Beginning conversation...");
		
		return getViewPath() + "manage.xhtml";
	}
	
	//public List<String> getListaEstados(){
		//logger.log(Level.INFO, "TO no getEstados...");

		//estados = procurarEstadoService.searchState();
		//return listaEstados;
	//}
	
	public List<String> getListaEstados() {
		
		
		listaEstados = new ArrayList<String>();
		listaEstadosNaoTratada = new ArrayList<String>();
		
		String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + //
					   "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + //
					   "PREFIX dct: <http://purl.org/dc/terms/>\r\n" + //
					   "\r\n" + //
					   "\r\n" + //
					   "SELECT ?statesLink ?states WHERE {\r\n" + //
					   "?link a skos:Concept;\r\n" + //
					   "rdfs:label ?pais.\r\n" + //
					   "?statesLink dct:subject ?link;\r\n" + //
					   "rdfs:label ?states.\r\n" + //
					   "FILTER (?pais = \"States of Brazil\"@en)\r\n" + //
					   "FILTER(LANG(?states)='pt')\r\n" + //
					   "}\r\n" + // 
					   "ORDER BY ?states";
	
	
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = queryExecution.execSelect();
	
		while (results.hasNext()) {
			
			QuerySolution querySolution = results.next();
			String e = querySolution.getLiteral("states").toString();
			e = e.replaceAll("@pt", "");
			listaEstadosNaoTratada.add(e);
			if(e.contains("(Brasil)")) {
				e = e.replaceAll("[()]", "");
				e = e.replaceAll("Brasil", "");
			}	
			if(e.contains("(estado)")) {
				e = e.replaceAll("[()]", "");
				e = e.replaceAll("estado", "");
			}
			

			if(!e.contentEquals("Unidades federativas do Brasil"))
				listaEstados.add(e);
		}
		
		queryExecution.close();
		return listaEstados;
	}
	
	
	/*public List<String> parseString() {
		listaEstados = new ArrayList<String>();
		listaEstados.add("1 3 4");
		listaEstados.add("1j 2");
		listaEstados.add("1 s 66");
		listaEstados.add("1 7 2");
		return listaEstados;
	}*/
	
	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}

	
	public void filtrarCidades() {
		
		nomeEstado = selectedEntity.getEstado();
		String str1 = "(Brasil)";
		String str2 = "(estado)";
		
		if(listaEstadosNaoTratada.contains(nomeEstado)) {
			logger.log(Level.INFO, "Beginning filtering cities...");
			int index = listaEstadosNaoTratada.indexOf(nomeEstado);
			String state = listaEstadosNaoTratada.get(index);
			logger.log(Level.INFO, "Retrieving cities from state " + state);

			listaCidades = new ArrayList<String>();
			listaCidades = pesquisarListaCidades(state);
		}	
		
		else if(nomeEstado.equals("Distrito Federal ")) {
				String aux = nomeEstado.concat(str1);
				logger.log(Level.INFO, "Retrieving cities from state " + aux);

				listaCidades = new ArrayList<String>();
				listaCidades = pesquisarListaCidades(aux);
		}
		else if(nomeEstado.equals("Espírito Santo ")) {
				String aux = nomeEstado.concat(str2);
				logger.log(Level.INFO, "Retrieving cities from state " + aux);

				listaCidades = new ArrayList<String>();
				listaCidades = pesquisarListaCidades(aux);
				
		}
		else logger.log(Level.INFO, "Could not retrieve cities from state " + nomeEstado);
		

	}

	public List<String> pesquisarListaCidades(String states) {
		
		
		
		String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + //
					   "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + //
					   "PREFIX dct: <http://purl.org/dc/terms/>\r\n" + //
					   "PREFIX dbo: <http://dbpedia.org/ontology/>\r\n" + //
					   "\r\n" + //
					   "\r\n" + //
					   "SELECT ?statesLink ?states ?citylink ?city WHERE {\r\n" + //
					   "?link a skos:Concept;\r\n" + //
					   "rdfs:label ?pais.\r\n" + //
					   "?statesLink dct:subject ?link;\r\n" + //
					   "rdfs:label ?states.\r\n" + //
					   "?citylink dbo:isPartOf ?statesLink;\r\n" + //
					   "rdfs:label ?city.\r\n" + //
					   "FILTER (?pais = \"States of Brazil\"@en)\r\n" + //
					   "FILTER(LANG(?states)='pt')\r\n" + //
					   "FILTER(LANG(?city)='pt')\r\n" + //
					   "FILTER(?states = \"" + states + "\"@pt)\r\n" + //
					   "}\r\n" + // 
					   "ORDER BY ?city";
	
	
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = queryExecution.execSelect();
	
		while (results.hasNext()) {
			
			QuerySolution querySolution = results.next();
			String c = querySolution.getLiteral("city").toString();
			//e = Normalizer.normalize(e, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			
			c = c.replaceAll("@pt", "");
			if(c.contains("("+nomeEstado+")")) {
				c = c.replaceAll("[()]", "");
				c = c.replaceAll(nomeEstado, "");
			}	
			if(c.contains("("+nomeEstado+" )")) {
				c = c.replaceAll("[()]", "");
				c = c.replaceAll(nomeEstado, "");
			}
			listaCidades.add(c);
		}
		
		queryExecution.close();
		return listaCidades;
	}


	public List<String> getListaCidades() {
		return listaCidades;
	}


	public void setListaCidades(List<String> listaCidades) {
		this.listaCidades = listaCidades;
	}




	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		
	}

}
