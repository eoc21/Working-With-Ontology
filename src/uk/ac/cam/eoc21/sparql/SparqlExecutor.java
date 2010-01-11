package sparql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
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
		Model model = ModelFactory.createMemModelMaker().createModel(null);
		model.read(in,null);
		in.close();
		// Create a new query
		String queryString = 
		//	"PREFIX foaf: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl/> " +
		//	"SELECT ?propertyURL " +
		//	"WHERE {" +
		//	"       ?foaf:hasUnit ?foaf:hasMeasurementTechnique \"DSC\" . "+
			"SELECT *"+
			"{ ?s ?p ?o "+
			"      }";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		qe.close();

	}

}
