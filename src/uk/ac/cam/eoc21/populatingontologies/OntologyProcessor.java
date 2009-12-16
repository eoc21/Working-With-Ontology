package populatingontologies;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * This class provides functionality to add extra information to the separate
 * ontologies and to merge ontologies.
 * 
 * @author ed
 * 
 */
public class OntologyProcessor {
	public OntologyProcessor() {

	}

	/**
	 * 
	 * @param ontology
	 *            - OntModel representation of an ontology.
	 * @param nameSpace
	 *            - Name space of the ontology.
	 * @param owlClassName
	 *            - String representation of an OWL class.
	 * @param instanceName
	 *            - String representation of an OWL individual.
	 */
	public static void addInstance(final OntModel ontology,
			final String nameSpace, final String owlClassName,
			final String instanceName) {
		OntClass owlClass = ontology.getOntClass(nameSpace + owlClassName);
		Individual anIndividual = ontology.createIndividual(nameSpace
				+ instanceName, owlClass);
		anIndividual.addComment("This is a test!!", instanceName);
		System.out.println(anIndividual.getComment(instanceName));
	}

	/**
	 * 
	 * @param ontologies
	 *            - ArrayList of the ontologies to merge.
	 * @re OntModel wnOntology = ModelFactory.createOntologyModel();
	 *     wnOntology.add(updatedOntology); wnOntology.add(techniquesOntology);
	 *     turn OntModel of the merged ontologies.
	 */
	public static OntModel mergeOntologies(final ArrayList<OntModel> ontologies) {
		OntModel mergedOntology = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		for (OntModel anOntology : ontologies) {
			mergedOntology.add(anOntology);
		}
		return mergedOntology;
	}
	/**
	 * 
	 * @param ontology - OntModel to extract all OntClasses.
	 * @return
	 */
	public static ArrayList<OntClass> getAllOWLClasses(final OntModel ontology){
		ExtendedIterator<OntClass> owlClasses = ontology.listClasses();
		ArrayList<OntClass> ontologyClasses = new ArrayList<OntClass>();
		while(owlClasses.hasNext()){
			ontologyClasses.add(owlClasses.next());
		}
		return ontologyClasses;
	}

}
