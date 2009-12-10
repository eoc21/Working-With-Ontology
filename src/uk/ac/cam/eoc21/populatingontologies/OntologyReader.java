package populatingontologies;
/**
 * Class to populate an ontology with Individuals using Jena.
 */
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Simple class to read in an OWL ontology into an ontModel in Jena.
 * 
 * @author eoc21
 * 
 */
public class OntologyReader {
	private String ontologyURI;
	private static final String NS = "http://www.polymerinformatics.com/ChemAxiom/ChemAxiomProp.owl#";
	private OntModel propertiesOntology;
	private Model propertiesOnt;
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
		propertiesOntology = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		setPropertiesOnt(propertiesOntology.read(ontologyURI, "RDF/XML"));
	}
	/**
	 * 
	 * @param ontology - OntModel representation of an ontology.
	 * @param owlClassName - String representation of an OWL class.
	 * @param instanceName - String representation of an OWL individual.
	 */
	public void addInstance(final OntModel ontology, final String owlClassName,
			final String instanceName) {
		OntClass owlClass = ontology.getOntClass(NS + owlClassName);
		Individual anIndividual = propertiesOntology.createIndividual(NS
				+ instanceName, owlClass);
		anIndividual.addComment("This is a test!!", instanceName);
		System.out.println(anIndividual.getComment(instanceName));
	}
	/**
	 * 
	 * @return OntModel - the ontology.
	 */
	public OntModel getOntologyModel() {
		return propertiesOntology;
	}

	public static void main(String[] args) {
		OntologyReader oReader = new OntologyReader(
				"http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomProp.owl");
		oReader.readOntology();
		System.out.println(oReader.getOntologyModel().size());
		oReader.addInstance(oReader.getOntologyModel(),
				"DielectricBreakdownVoltage", "TEST");
		System.out.println(oReader.getOntologyModel().size());
		oReader.addInstance(oReader.getOntologyModel(),
				"DielectricBreakdownVoltage", "TEST1");
		System.out.println(oReader.getOntologyModel().size());
		System.out.println(oReader.getOntologyModel().getIndividual(
				OntologyReader.NS + "TEST"));
	}
	/**
	 * 
	 * @param propertiesOnt - RDF model for ChemAxiomProperties ontology.
	 */
	public void setPropertiesOnt(Model propertiesOnt) {
		this.propertiesOnt = propertiesOnt;
	}
	/**
	 * 
	 * @return RDFModel representation of the ontology.
	 */
	public Model getPropertiesOnt() {
		return propertiesOnt;
	}
}
