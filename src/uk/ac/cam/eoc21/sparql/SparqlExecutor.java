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
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * Reads in an RDF file an executes a SPARQL query nad returns the result set.
 * @author ed
 *
 */
public class SparqlExecutor {
	private static final String PROP_PREFIX = "PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#>";
	private static final String RDF_PREFIX = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	private static final String REPEATUNIT_PREFIX = "PREFIX ru: <http://www.chemoinformatician.co.uk/RepeatUnits.owl#>";
	private static final String MEASUREMENT_TECHNIQUE_PREFIX = "PREFIX mtechnique: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomMetrology.owl#>";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InputStream in = new FileInputStream(new File(args[0]));
		Model model = ModelFactory.createMemModelMaker().createModel(null);
		model.read(in,null);
		in.close();
		//Get a count of the rdf statements in the merged ontology.
/*		StmtIterator iter = model.listStatements();
		int counter=0;
		while(iter.hasNext()){
			Statement stmt = iter.nextStatement();
			System.out.println(stmt.toString());
			counter++;
			System.out.println(counter);
		}*/
		//System.out.println("Number of RDF triples:"+ counter);
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
			"PREFIX metrology: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomMetrology.owl#>"+
			"SELECT *"+
			"WHERE {"+"?x prop:hasValue ?hasValue." +	
			"?x prop:hasMeasurementTechnique ?technique."+
	//		"?x rdf:type <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#DielectricConstant>."+
			"?x metrology:hasCondition ?condition."+
			"FILTER(?hasValue > 200)"+
			"FILTER regex(?technique, \"DSC\", \"i\")"+
		//	"FILTER regex(?condition, \"temperature ca 357\",\"i\")"+
			"}"+
			"LIMIT 100";
		String queryWithFilter = 
		"PREFIX prop: <http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#> " +	
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
		"SELECT *"+
		"WHERE{?x prop:hasMeasurementTechnique ?val." +
		"FILTER regex(?val, \"GPC\", \"i\")"+
		"}" +
		"LIMIT 200";

		//						"SELECT *"+
		//	"{ ?s ?p ?o "+
		//	"      }";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryValues);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		//Time the query
		long startTime = System.currentTimeMillis();
		ResultSet results = qe.execSelect();
		long endTime = System.currentTimeMillis();
		long totalTime =  endTime-startTime;
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		qe.close();
		System.out.println("Total time:"+totalTime);

	}

}
