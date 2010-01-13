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
			"PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#> " +	
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"SELECT *"+
			"WHERE {"+"?x prop:hasMeasurementTechnique ?hasMeasurementTechnique.}"+
			"LIMIT 10";
		
		String queryValues = 
			"PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#> " +	
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
			"SELECT *"+
			"WHERE {"+"?x prop:hasValue ?hasValue." +		
			"}"+
			"LIMIT 10";
		
		String queryWithFilter = 
		"PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#> " +	
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
		"SELECT *"+
		"WHERE{?x prop:hasValue ?val." +
		"FILTER regex(?val, \"1.436\", \"i\")"+
		"}" +
		"LIMIT 200";
		//						"SELECT *"+
		//	"{ ?s ?p ?o "+
		//	"      }";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryWithFilter);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		qe.close();

	}

}
