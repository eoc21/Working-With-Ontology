package sparql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Reads in an RDF file an executes a SPARQL query nad returns the result set.
 * @author ed
 *
 */
public class SparqlExecutor {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InputStream in = new FileInputStream(new File(args[0]));
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontologyModel.read(in, null);
		in.close();

	}

}
