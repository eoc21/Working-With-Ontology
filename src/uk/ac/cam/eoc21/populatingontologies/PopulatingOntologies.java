package populatingontologies;

import java.net.URI;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLAnnotationAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLIndividualAxiom;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyStorageException;
import org.semanticweb.owl.model.OWLPropertyAxiom;
import org.semanticweb.owl.model.UnknownOWLOntologyException;
import org.semanticweb.owl.util.OWLOntologyMerger;

/**
 * This class populates the ontology with instances using the OWL API.
 * 
 * @author eoc21
 * 
 */
public class PopulatingOntologies {
	private static final String PROPERTY_URI = "http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomProp.owl";
	private static String REPEATUNIT_URI = "http://chemoinformatician.co.uk/RepeatUnitRDF.owl";
	private static final String MEASUREMENT_TECHNIQUES_URI = "http://ontologies.googlecode.com/svn/trunk/src/ChemAxiomMetrology.owl";
	private static final String MEASUREMENT_STANDARDS_URI = "http://ontologies.googlecode.com/svn/trunk/src/MeasurementStandards.owl";
	private static final String MEASUREMENT_CONDITIONS_URI = "http://ontologies.googlecode.com/svn/trunk/src/MeasurementConditionIntegrated.owl";
	private static final String UNITS_URI = "http://ontologies.googlecode.com/svn/trunk/src/DumontierUnitsComplex.owl";
	private static final String DUBLIN_CORE = "http://protege.stanford.edu/plugins/owl/dc/protege-dc.owl";
	private static OWLOntology measurementTechniqueOntology;
	/**
	 * 
	 * @param measurementTechniqueOntology2 - OWLOntology.
	 */
	public static void setMeasurementTechniqueOntology(
			OWLOntology measurementTechniqueSOntology) {
		PopulatingOntologies.measurementTechniqueOntology = measurementTechniqueSOntology;
	}
	/**
	 * 
	 * @return OWLOntology
	 */
	public static OWLOntology getMeasurementTechniqueOntology2() {
		return measurementTechniqueOntology;
	}
	
	public static OWLIndividual createOWLIndividual(){
		return null;
	}

	public static void main(String[] args) throws UnknownOWLOntologyException,
			OWLOntologyStorageException, OWLOntologyChangeException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		URI propertiesURI = URI.create(PROPERTY_URI);
		URI measurementTechniquesURI = URI.create(MEASUREMENT_TECHNIQUES_URI);
		URI repeatUnitURIValue = URI.create(REPEATUNIT_URI);
		URI measurementStandardURI = URI.create(MEASUREMENT_STANDARDS_URI);
		URI measurementConditionsURI = URI.create(MEASUREMENT_CONDITIONS_URI);
		URI unitsURI = URI.create(UNITS_URI);
		URI dublinCoreURI = URI.create(DUBLIN_CORE);
		try {
			//Load up the ontologies
			OWLOntology propertiesOntology = man.loadOntologyFromPhysicalURI(propertiesURI);
			OWLOntology repeatUnitsOntology = man.loadOntology(repeatUnitURIValue);
			OWLOntology measurementStandardsOntology = man.loadOntology(measurementStandardURI);
			OWLOntology measurementConditionsOntology = man.loadOntology(measurementConditionsURI);
			OWLOntology unitsOntology = man.loadOntology(unitsURI);
			OWLOntology dublinCore = man.loadOntology(dublinCoreURI);
			OWLOntologyMerger merger = new OWLOntologyMerger(man);
			URI mergedOntologyURI = URI.create("http://www.semanticweb.com/mymergedont");
			setMeasurementTechniqueOntology(man
					.loadOntology(measurementTechniquesURI));
			//Create a data factory to populate instances.
			OWLDataFactory dataFactory = man.getOWLDataFactory();
			OWLIndividual pmma = dataFactory.getOWLIndividual(URI.create(REPEATUNIT_URI
					+ "#PMMA"));
			OWLIndividual pmmaSample = dataFactory.getOWLIndividual(URI.create(REPEATUNIT_URI+"#PMMASample1"));
			OWLIndividual pmmaBpt = dataFactory.getOWLIndividual(URI.create(PROPERTY_URI
					+ "#PMMA_BP"));
			OWLDataProperty hasValue = dataFactory.getOWLDataProperty(URI
					.create(PROPERTY_URI + "#hasValue"));
			OWLDataProperty hasTg = dataFactory.getOWLDataProperty(URI
					.create(PROPERTY_URI + "#hasTg"));
			OWLDataPropertyAssertionAxiom dataAssertion = dataFactory
					.getOWLDataPropertyAssertionAxiom(pmmaBpt, hasValue, 200);
			OWLDataPropertyAssertionAxiom dataAssertion1 = dataFactory
					.getOWLDataPropertyAssertionAxiom(pmma, hasTg, 78.8);
			OWLObjectProperty hasBoilingPoint = dataFactory
					.getOWLObjectProperty(URI.create(PROPERTY_URI + "#hasBoilingPoint"));
			OWLObjectProperty hasSample = dataFactory.getOWLObjectProperty(URI.create(REPEATUNIT_URI+"#hasSample"));
			OWLObjectPropertyAssertionAxiom assertion = dataFactory
					.getOWLObjectPropertyAssertionAxiom(pmma, hasBoilingPoint,
							pmmaBpt);
			OWLObjectPropertyAssertionAxiom polyPolySample = dataFactory.getOWLObjectPropertyAssertionAxiom(pmma,hasSample,pmmaSample);
			AddAxiom addAxiomChange = new AddAxiom(propertiesOntology, assertion);
			AddAxiom addAxiomChange1 = new AddAxiom(propertiesOntology, dataAssertion);
			AddAxiom addAxiomChange2 = new AddAxiom(propertiesOntology, dataAssertion1);
			try {
				man.applyChange(addAxiomChange);
				man.applyChange(addAxiomChange1);
				man.applyChange(addAxiomChange2);
				man.addAxiom(repeatUnitsOntology, polyPolySample);
				OWLOntology merged = merger.createMergedOntology(man, mergedOntologyURI);
				man.saveOntology(merged, new RDFXMLOntologyFormat(), URI.create("file:/tmp/mergedont.owl"));
			} catch (OWLOntologyChangeException e) {
				e.printStackTrace();
			}
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

}
