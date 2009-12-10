package populatingontologies;

import java.net.URI;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLAnnotationAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyStorageException;
import org.semanticweb.owl.model.UnknownOWLOntologyException;

/**
 * This class populates the ontology with instances using the OWL API.
 * 
 * @author eoc21
 * 
 */
public class PopulatingOntologies {
	private static String uri = "http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomProp.owl";
	private static final String measurementTechniqueOntology = "http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomMetrology.owl";
	private static OWLOntology measurementTechniqueOntology2;
	/**
	 * 
	 * @param measurementTechniqueOntology2 - OWLOntology.
	 */
	public static void setMeasurementTechniqueOntology2(
			OWLOntology measurementTechniqueOntology2) {
		PopulatingOntologies.measurementTechniqueOntology2 = measurementTechniqueOntology2;
	}
	/**
	 * 
	 * @return OWLOntology
	 */
	public static OWLOntology getMeasurementTechniqueOntology2() {
		return measurementTechniqueOntology2;
	}

	public static void main(String[] args) throws UnknownOWLOntologyException,
			OWLOntologyStorageException, OWLOntologyChangeException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		URI physicalURI = URI.create(uri);
		URI physicalPolyClassURI = URI.create(measurementTechniqueOntology);
		try {
			OWLOntology ontology = man.loadOntologyFromPhysicalURI(physicalURI);
			//Iterate over all the owl classes and for each class extract all the associated axioms
			for(OWLClass cls : ontology.getReferencedClasses()) {
				Set<OWLAnnotation> axiomsAnnt = cls.getAnnotations(ontology);
				System.out.println(cls);
				Iterator<OWLAnnotation> axiomIter = axiomsAnnt.iterator();
				while(axiomIter.hasNext()){
					System.out.println(axiomIter.next().toString());
				}
			}
			setMeasurementTechniqueOntology2(man
					.loadOntology(physicalPolyClassURI));
			OWLDataFactory dataFactory = man.getOWLDataFactory();
			OWLIndividual pmma = dataFactory.getOWLIndividual(URI.create(uri
					+ "#PMMA"));
			
			OWLIndividual pmmaBpt = dataFactory.getOWLIndividual(URI.create(uri
					+ "#PMMA_BP"));
//			Set<OWLAxiom> allPropertyAxioms = ontology.getAxioms();
//			Iterator<OWLAxiom> polymerpropertyAxiomIter = allPropertyAxioms.iterator();
//			while(polymerpropertyAxiomIter.hasNext()){
//				System.out.println(polymerpropertyAxiomIter.next().toString());
//			}
			OWLIndividual pbmaBpt = dataFactory.getOWLIndividual(URI.create(uri+"#PBMA_BP"));
			OWLDataProperty hasValue = dataFactory.getOWLDataProperty(URI
					.create(uri + "#hasValue"));
			OWLDataProperty hasTg = dataFactory.getOWLDataProperty(URI
					.create(uri + "#hasTg"));
			OWLDataPropertyAssertionAxiom dataAssertion = dataFactory
					.getOWLDataPropertyAssertionAxiom(pmmaBpt, hasValue, 200);
			OWLDataPropertyAssertionAxiom dataAssertionX = dataFactory.getOWLDataPropertyAssertionAxiom(pbmaBpt, hasValue, 175);
			
			OWLDataPropertyAssertionAxiom dataAssertion1 = dataFactory
					.getOWLDataPropertyAssertionAxiom(pmma, hasTg, 78.8);
			OWLObjectProperty hasBoilingPoint = dataFactory
					.getOWLObjectProperty(URI.create(uri + "#hasBoilingPoint"));
			OWLObjectPropertyAssertionAxiom assertion = dataFactory
					.getOWLObjectPropertyAssertionAxiom(pmma, hasBoilingPoint,
							pmmaBpt);
			AddAxiom addAxiomChange = new AddAxiom(ontology, assertion);
			AddAxiom addAxiomChange1 = new AddAxiom(ontology, dataAssertion);
			AddAxiom addAxiomChange2 = new AddAxiom(ontology, dataAssertion1);
			AddAxiom addAxiomChange3 = new AddAxiom(ontology,dataAssertionX);
			Set<OWLIndividual> vals = pbmaBpt.getSameIndividuals(ontology);
			Iterator<OWLIndividual> owlIiter = vals.iterator();
			//while (owlIiter.hasNext()){
			//	OWLIndividual v = owlIiter.next();
			//	System.out.println(v.getURI());
			//}
			
			try {
				man.applyChange(addAxiomChange);
				man.applyChange(addAxiomChange1);
				man.applyChange(addAxiomChange2);
				man.applyChange(addAxiomChange3);
				man.saveOntology(ontology, URI.create("file:/example.owl"));
			} catch (OWLOntologyChangeException e) {
				e.printStackTrace();
			}
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

}
