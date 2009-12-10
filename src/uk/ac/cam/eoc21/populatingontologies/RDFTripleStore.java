package populatingontologies;

import java.util.ArrayList;

/**
 * Class holder for a list of triples.
 * @author eoc21
 *
 */
public class RDFTripleStore {
	private ArrayList<RDFTriple> triples;
	
	/**
	 * 
	 * @param tripleList - ArrayList<RDFTriple> contains all RDF triples for ontology population.
	 */
	public RDFTripleStore(ArrayList<RDFTriple> tripleList){
		this.triples = tripleList;
	}
	
	public RDFTripleStore() {
	}

	/**
	 * 
	 * @return ArrayList<RDFTriple>
	 */
	public ArrayList<RDFTriple>getTripleList(){
		return triples;
	}
	/**
	 * 
	 * @param tripleStore - sets all triples.
	 */
	public void setTriples(ArrayList<RDFTriple> tripleStore){
		this.triples = tripleStore;
	}
}
