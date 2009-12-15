package populatingontologies;
/**
 * Class to populate an ontology with Individuals using Jena.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Simple class to read in an OWL ontology into an ontModel in Jena. The class will allow users to populate the ontology with 
 * individuals, and finally merge the ontologies together.
 * 
 * @author eoc21
 * 
 */
public class OntologyReader {
	private String ontologyURI;
	private OntModel ontologyModel;
	private Model rdfOntologyModel;
	/**
	 * 
	 * @param uri - Ontology uri.
	 */
	public OntologyReader(final String uri) {
		this.ontologyURI = uri;
	}
	/**
	 * Method to read in an ontology from a URI.
	 */
	private void readOntology() {
		ontologyModel = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		setRDFModel(ontologyModel.read(ontologyURI, "RDF/XML"));
	}
	/**
	 * 
	 * @return OntModel - the ontology.
	 */
	public OntModel getOntologyModel() {
		return ontologyModel;
	}
	/**
	 * 
	 * @param propertiesOnt - RDF model for ChemAxiomProperties ontology.
	 */
	private void setRDFModel(final Model propertiesOnt) {
		this.rdfOntologyModel = propertiesOnt;
	}
	/**
	 * 
	 * @return RDFModel representation of the ontology.
	 */
	public Model getPropertiesOnt() {
		return rdfOntologyModel;
	}

	public static void main(String[] args) throws IOException {
		OntologyReader oReader = new OntologyReader(
				"http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomProp.owl");
		oReader.readOntology();
		System.out.println(oReader.getOntologyModel().size());
		OntologyProcessor.addInstance(oReader.getOntologyModel(),OntologyNameSpaceDictionary.PROPERTY_NS,
				"DielectricBreakdownVoltage", "TEST");
		System.out.println(oReader.getOntologyModel().size());
		OntologyProcessor.addInstance(oReader.getOntologyModel(),OntologyNameSpaceDictionary.PROPERTY_NS,
				"DielectricBreakdownVoltage", "TEST3");
		System.out.println(oReader.getOntologyModel().size());
		OntModel updatedOntology = oReader.getOntologyModel();
		//Merge in the  measurement techniques.
		OntologyReader techniques = new OntologyReader("http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomMetrology.owl");
		techniques.readOntology();
		OntModel techniquesOntology = techniques.getOntologyModel();
		OntologyReader conditions = new OntologyReader(OntologyNameSpaceDictionary.MEASUREMENT_CONDITIONS_URI);
		conditions.readOntology();
		OntologyReader units = new OntologyReader(OntologyNameSpaceDictionary.UNITS_URI);
		units.readOntology();
		OntologyReader standards = new OntologyReader(OntologyNameSpaceDictionary.MEASUREMENT_STANDARDS_URI);
		standards.readOntology();
		ArrayList<OntModel> ontologies = new ArrayList<OntModel>();
		ontologies.add(updatedOntology);
		ontologies.add(techniquesOntology);
		ontologies.add(conditions.getOntologyModel());
		ontologies.add(units.getOntologyModel());
		ontologies.add(standards.getOntologyModel());
		OntModel mergedOntology = OntologyProcessor.mergeOntologies(ontologies);
		FileWriter fw = new FileWriter("TestOntologyOutput.owl");
		mergedOntology.write(fw);
	}
}
