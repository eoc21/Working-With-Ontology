package xmlextraction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.XSD;

import populatingontologies.Mapper;
import populatingontologies.OntologyNameSpaceDictionary;
import populatingontologies.OntologyProcessor;
import populatingontologies.OntologyReader;
import populatingontologies.PolyInfo2ChemAxiomPropertyMapper;
import populatingontologies.RDFTriple;
import populatingontologies.RDFTripleStore;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;

/**
 * Extracts information from repeat unit xml for populating an ontology with
 * triples. (In the first instance we will use more DatatypeProperty values
 * than ObjecttypeProperty values.
 * 
 * @author eoc21
 * 
 */
public class RepeatUnitInformationExtractor {
	/**
	 * RDF - Predicates.
	 */
	private static final String HAS_SAMPLE = "hasSample";
	private static final String HAS_PROPERTY = "hasProperty";
	private static final String HAS_UNIT = "hasUnit";
	private static final String HAS_VALUE = "hasValue";
	private static final String HAS_METHOD = "hasMethod";
	private static final String HAS_CONDITION = "hasCondition";
	/**
	 * Stores all the rdf triples for input into the ontology.
	 */
	private RDFTripleStore rdfTriples;

	public RepeatUnitInformationExtractor() {
	}

	/**
	 * 
	 * @param rdfStore
	 *            - ArrayList<RDFTriple>
	 */
	public void setTripleStore(RDFTripleStore rdfStore) {
		this.setRdfTriples(rdfStore);
	}

	/**
	 * 
	 * @param f
	 *            - File to read repeat unit xml information from.
	 * @return - XOM Document.
	 */
	private static Document readXML(final File f) {
		Document doc = null;
		try {
			Builder parser = new Builder();
			doc = parser.build(f);
		} catch (ParsingException ex) {
			System.err.println("Repeat unit XML is malformed!");
		} catch (IOException ex) {
			System.err.println("Could not open file!");
		}
		return doc;

	}

	/**
	 * 
	 * @param document
	 *            - XOM Document representation of repeat unit information.
	 * @return - RepeatUnit object.
	 */
	private static RepeatUnit processRepeatUnit(final Document document) {
		RepeatUnit repeatUnit = new RepeatUnit();
		Nodes sampleNodes = document.query("//Sample");
		ArrayList<RepeatUnitSample> sampleList = new ArrayList<RepeatUnitSample>();
		for (int a = 0; a < sampleNodes.size(); a++) {
			RepeatUnitSample aSample = new RepeatUnitSample();
			Node aNode = sampleNodes.get(a);
			Nodes id = aNode.query("attribute::id");
			aSample.setId(id.get(0).getValue());
			Nodes refs = aNode.query("child::Reference");
			Nodes additives = aNode.query("child::Additives");
			String additiveValue;
			if (additives.size() == 0) {
				additiveValue = "";
			} else {
				additiveValue = additives.get(0).getValue();
			}
			String reference;
			if (refs.size() == 0) {
				reference = "";
			} else {
				reference = refs.get(0).getValue();
			}
			aSample.setAdditives(additiveValue);
			aSample.setReference(reference);
			Nodes properties = aNode.query("child::Property");
			ArrayList<PolymerProperty> propertiesList = new ArrayList<PolymerProperty>();
			for (int i = 0; i < properties.size(); i++) {
				Node aProperty = properties.get(i);
				PolymerProperty property = new PolymerProperty();
				Nodes propertyId = aProperty.query("attribute::id");
				Nodes unit = aProperty.query("attribute::unit");
				Nodes value = aProperty.query("attribute::value");
				if (propertyId.size() == 0) {
					property.setId("");
				} else {
					property.setId(propertyId.get(0).getValue());
				}
				if (unit.size() == 0) {
					property.setUnit("");
				} else {
					property.setUnit(unit.get(0).getValue());
				}
				if (value.size() == 0) {
					property.setValue("");
				} else {
					property.setValue(value.get(0).getValue());
				}
				ArrayList<DataProperty> dataArray = new ArrayList<DataProperty>();
				Nodes dataNodes = aProperty.query("child::Data");
				for (int j = 0; j < dataNodes.size(); j++) {
					Node aDataNode = dataNodes.get(j);
					Nodes dataNodeId = aDataNode.query("attribute::id");
					DataProperty dataProperty = new DataProperty();
					if (dataNodeId.size() == 0) {
						dataProperty.setId("");
					} else {
						dataProperty.setId(dataNodeId.get(0).getValue());
					}
					dataProperty.setValue(aDataNode.getValue());
					dataArray.add(dataProperty);
				}
				propertiesList.add(property);
				property.setDataProperties(dataArray);
			}
			aSample.setProperties(propertiesList);
			sampleList.add(aSample);
		}
		repeatUnit.setSamples(sampleList);
		return repeatUnit;
	}

	/**
	 * 
	 * @param rdfTriples
	 *            - RDFTripleStore to store all the triples.
	 */
	public void setRdfTriples(RDFTripleStore rdfTriples) {
		this.rdfTriples = rdfTriples;
	}

	/**
	 * 
	 * @return - RDFTripleStore.
	 */
	public RDFTripleStore getRdfTriples() {
		return rdfTriples;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<RDFTriple> triples = new ArrayList<RDFTriple>();
		RepeatUnit repeatUnit = new RepeatUnit();
		OntologyReader oReader = new OntologyReader(OntologyNameSpaceDictionary.REPEATUNIT_URI);
		oReader.readOntology();
		//Read in properties ontology
		OntologyReader propertiesOntologyReader = new OntologyReader(OntologyNameSpaceDictionary.PROPERTY_URI);
		propertiesOntologyReader.readOntology();
		DatatypeProperty hasValue = propertiesOntologyReader.getOntologyModel().getDatatypeProperty(OntologyNameSpaceDictionary.PROPERTY_NS + "hasValue");
		hasValue.setRange(XSD.xdouble);
		//Need to map properties to ontology properties
		Mapper m = new Mapper(new File(args[3]));
		ArrayList<PolyInfo2ChemAxiomPropertyMapper> polyInfo2ChemAxiomPropMap = m.mapProperties();
		ObjectProperty hasSample = oReader.getOntologyModel().getObjectProperty(OntologyNameSpaceDictionary.REPEATUNIT_NS + "hasSample");
		//Read in units ontology
		OntologyReader unitsOntologyReader = new OntologyReader(OntologyNameSpaceDictionary.UNITS_URI);
		unitsOntologyReader.readOntology();
		DatatypeProperty hasUnit = propertiesOntologyReader.getOntologyModel().createDatatypeProperty(OntologyNameSpaceDictionary.PROPERTY_NS + "hasUnit");
		DatatypeProperty hasMeaurementTechnique = propertiesOntologyReader.getOntologyModel().createDatatypeProperty(OntologyNameSpaceDictionary.PROPERTY_NS + "hasMeasurementTechnique"); 
		ObjectProperty hasPropertyOf = oReader.getOntologyModel().createObjectProperty(OntologyNameSpaceDictionary.REPEATUNIT_NS + "hasProperty");
		//Measurement condition / technique
		OntologyReader measurementTechniquesOntologyReader = new OntologyReader(OntologyNameSpaceDictionary.MEASUREMENT_TECHNIQUES_URI);
		measurementTechniquesOntologyReader.readOntology();
		DatatypeProperty hasCondition = measurementTechniquesOntologyReader.getOntologyModel().createDatatypeProperty(OntologyNameSpaceDictionary.MEASUREMENT_TECHNIQUES_NS + "hasCondition");
		
		File dir = new File(args[0]);
		// String repeatUnitIdWithUnitAndValue;
		String repeatUnitIdfile;
		String sampleId;
		String unitValue;
		String propValue;
		String propId;
		String[] children = dir.list();
		Set<String> measurementTechniquesSet = new HashSet<String>();
		Set<String> measurementConditionsSet = new HashSet<String>();
		Set<String> unitsSet = new HashSet<String>();
		Set<String> uniqueProperties = new HashSet<String>();
		for (int i = 0; i < children.length; i++) {
			System.out.println(i);
			repeatUnitIdfile = children[i].replace(".xml", "");
			String fileName = dir + "/" + children[i];
			Document document = RepeatUnitInformationExtractor
					.readXML(new File(fileName));
			repeatUnit = RepeatUnitInformationExtractor
					.processRepeatUnit(document);
			//Add polymer instances.
			Individual repeatUnitInstance = OntologyProcessor.addInstance(oReader.getOntologyModel(),OntologyNameSpaceDictionary.REPEATUNIT_NS,
					"Polymer", repeatUnitIdfile);
			for (int j = 0; j < repeatUnit.getRepeatUnitSamples().size(); j++) {
				// Populate rdf triple store
				sampleId = repeatUnit.getRepeatUnitSamples().get(j).getId();
				//Add polymer samples to ontology here!
				Individual repeatUnitSampleInstance = OntologyProcessor.addInstance(oReader.getOntologyModel(), OntologyNameSpaceDictionary.REPEATUNIT_NS,"PolymerSample",sampleId);
				repeatUnitInstance.addProperty(hasSample, repeatUnitSampleInstance);
				RDFTriple ruTriple = new RDFTriple(repeatUnitIdfile,
						HAS_SAMPLE, sampleId);
				triples.add(ruTriple);
				for (int k = 0; k < repeatUnit.getRepeatUnitSamples().get(j)
						.getProperties().size(); k++) {
					unitsSet.add(repeatUnit.getRepeatUnitSamples().get(j)
							.getProperties().get(k).getUnit());
					unitValue = repeatUnit.getRepeatUnitSamples().get(j)
							.getProperties().get(k).getUnit();
					propValue = repeatUnit.getRepeatUnitSamples().get(j)
							.getProperties().get(k).getValue();
					propId = repeatUnit.getRepeatUnitSamples().get(j)
							.getProperties().get(k).getId();
					//Need to map propId now to OWL class name
					String mappedPropId = "";
					for(PolyInfo2ChemAxiomPropertyMapper prop : polyInfo2ChemAxiomPropMap){
						if ((prop.getPolyInfoProperty() != "NULL") && propId.equals(prop.getPolyInfoProperty())){
							mappedPropId = prop.getChemAxiomPropertyValue();
						}
					}
					Individual repeatUnitSamplePropertyInstance = OntologyProcessor.addInstance(propertiesOntologyReader.getOntologyModel(), OntologyNameSpaceDictionary.PROPERTY_NS,mappedPropId,repeatUnitIdfile+"_"+sampleId+"_"+mappedPropId);
					repeatUnitSampleInstance.hasProperty(hasPropertyOf,repeatUnitSamplePropertyInstance);
					//Convert property value into  a double so SPARQL query works for >, <  searches etc.
					Literal propertyValueLiteral = propertiesOntologyReader.getOntologyModel().createTypedLiteral(propValue,XSDDatatype.XSDdouble);
					Statement propertyHasValue = propertiesOntologyReader.getOntologyModel().createStatement(repeatUnitSamplePropertyInstance, hasValue,propertyValueLiteral);
					propertiesOntologyReader.getOntologyModel().add(propertyHasValue);
					//
					//repeatUnitSamplePropertyInstance.addProperty(hasValue,propValue);	
					Individual repeatUnitSamplePropertyUnit = OntologyProcessor.addInstance(unitsOntologyReader.getOntologyModel(), OntologyNameSpaceDictionary.UNITS_NS, "Unit", repeatUnitIdfile+"_"+sampleId+"_"+unitValue);
					repeatUnitSamplePropertyInstance.addProperty(hasUnit,unitValue); //.addProperty(hasUnit, repeatUnitSamplePropertyUnit);
					uniqueProperties.add(repeatUnit.getRepeatUnitSamples().get(j).getProperties().get(k).getId());
					if (unitValue != " " || sampleId != " ") {
						RDFTriple samplePropertyTriple = new RDFTriple(
								repeatUnitIdfile + "_" + sampleId,
								HAS_PROPERTY, repeatUnitIdfile + "_" + sampleId
										+ "_" + propId);
						triples.add(samplePropertyTriple);
					}
					if (propValue != " " || unitValue != " ") {
						RDFTriple propertyUnitTriple = new RDFTriple(
								repeatUnitIdfile + "_" + sampleId + "_"
										+ propId, HAS_UNIT, unitValue);
						triples.add(propertyUnitTriple);
					}
					if (propId != " " || propValue != " ") {
						RDFTriple propertyValueTriple = new RDFTriple(
								repeatUnitIdfile + "_" + sampleId + "_"
										+ propId, HAS_VALUE, repeatUnitIdfile
										+ "_" + sampleId + "_" + propId + "_"
										+ propValue);
						triples.add(propertyValueTriple);
					}
					for (int l = 0; l < repeatUnit.getRepeatUnitSamples()
							.get(j).getProperties().get(k).getDataProperties()
							.size(); l++) {
						Individual measurementTechniqueInstance = null;
						if (repeatUnit.getRepeatUnitSamples().get(j)
								.getProperties().get(k).getDataProperties()
								.get(l).getId().equals("Method")) {

							String measurementTechnique = repeatUnit
									.getRepeatUnitSamples().get(j)
									.getProperties().get(k).getDataProperties()
									.get(l).getValue();
							if (measurementTechnique != " ") {
								RDFTriple propertyTechniqueTriple = new RDFTriple(
										repeatUnitIdfile + "_" + sampleId + "_"
												+ propId, HAS_METHOD,
										repeatUnitIdfile + "_" + sampleId + "_"
												+ measurementTechnique);
								//Add triple for property has measurement technique
							//	measurementTechniqueInstance = OntologyProcessor.addInstance(measurementTechniquesOntologyReader.getOntologyModel(), OntologyNameSpaceDictionary.MEASUREMENT_TECHNIQUES_NS, "ExperimentalTechnique", measurementTechnique);
								repeatUnitSamplePropertyInstance.addProperty(hasMeaurementTechnique,measurementTechnique);//TODO need to change measurementTechniques to objects not data types.
								triples.add(propertyTechniqueTriple);
							}
							measurementTechniquesSet.add(repeatUnit
									.getRepeatUnitSamples().get(j)
									.getProperties().get(k).getDataProperties()
									.get(l).getValue());
						} else if (repeatUnit.getRepeatUnitSamples().get(j)
								.getProperties().get(k).getDataProperties()
								.get(l).getId().equals("Condition")) {
							String measurementCondition = repeatUnit
									.getRepeatUnitSamples().get(j)
									.getProperties().get(k).getDataProperties()
									.get(l).getValue();
							repeatUnitSamplePropertyInstance.addProperty(hasCondition, measurementCondition);//TODO need to change measurementCondition to ObjectTypeProperty
			//				measurementTechniqueInstance.addProperty(hasCondition, measurementCondition);
							if (measurementCondition != " ") {
								RDFTriple propertyConditionTriple = new RDFTriple(
										repeatUnitIdfile + "_" + sampleId + "_"
												+ propId, HAS_CONDITION,
										repeatUnitIdfile + "_" + sampleId + "_"
												+ measurementCondition);
								triples.add(propertyConditionTriple);
							}
							measurementConditionsSet.add(repeatUnit
									.getRepeatUnitSamples().get(j)
									.getProperties().get(k).getDataProperties()
									.get(l).getValue());
						}
					}
				}
			}
		}
		ArrayList<OntModel> ontologies = new ArrayList<OntModel>();
		ontologies.add(oReader.getOntologyModel());
		ontologies.add(propertiesOntologyReader.getOntologyModel());
		ontologies.add(measurementTechniquesOntologyReader.getOntologyModel());
		OntModel mergedOntology = OntologyProcessor.mergeOntologies(ontologies);
		//FileWriter fw = new FileWriter("PropertiesBlahBlah.owl");
		//propertiesOntologyReader.getOntologyModel().write(fw);
		FileWriter fw = new FileWriter("MergedBlahBlah.owl");
		mergedOntology.write(fw);
/*		FileWriter bw = new FileWriter(args[1]);
		for (RDFTriple rt : triples) {
			bw.write(rt.getSubject() + ":" + rt.getPredicate() + ":"
			+ rt.getObject()+"\n");
		}
		bw.close();
		  Iterator it = uniqueProperties.iterator(); 
		  int counter = 0;
		  FileWriter uniquePropertyFile = new FileWriter(args[2]); 
		  while (it.hasNext()) {
			  uniquePropertyFile.write(it.next().toString()+"\n");
		  counter++; } 
		  uniquePropertyFile.close(); 
		  System.out.println("Number of properties:" +counter);
	*/	 
	}
}
