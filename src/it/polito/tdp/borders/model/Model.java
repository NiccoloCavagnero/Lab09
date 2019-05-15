package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> countryIdMap;
	private List<Border> listaConfini;
	BordersDAO dao;
	

	public Model() {
		dao = new BordersDAO();
		countryIdMap = new HashMap<>();
		dao.loadAllCountries(countryIdMap);
	}
	
	public void generaGrafo(int year) {
		
		grafo = new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		
		listaConfini = dao.getCountryPairs(year, countryIdMap);
		
		Graphs.addAllVertices(grafo, countryIdMap.values());
		
		for ( Border b : listaConfini ) {
			grafo.addEdge(b.getSt1(), b.getSt2());
		}
		
		for (Country c : countryIdMap.values()) 
			if ( grafo.degreeOf(c) == 0 ) 
				grafo.removeVertex(c);
			
		System.out.print("GRAFO CREATO\n#VERTICI: "+grafo.vertexSet().size()+"\n#ARCHI: "+grafo.edgeSet().size()+"\n\n");
	}
	
	/**
	 * Metodo per ottenere il numero di componenti connesse (sottografi connessi) del grafo
	 * @return #componentiConnesse
	 */
	public int getNumComponentiConnesse() {
		ConnectivityInspector<Country, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
		return inspector.connectedSets().size();
	}
	
	public List<Country> search(Country source){
		
        List<Country> lista = new ArrayList<Country>();
		
		
		GraphIterator<Country, DefaultEdge> it = new DepthFirstIterator<>(this.grafo,source);
		
		while(it.hasNext()) {
			lista.add(it.next());
			System.out.println(it);
		}
		
		return lista.subList(1, lista.size());

	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<Country, DefaultEdge> grafo) {
		this.grafo = grafo;
	}

}
