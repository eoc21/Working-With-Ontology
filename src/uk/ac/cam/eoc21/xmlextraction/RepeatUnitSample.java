package xmlextraction;

import java.util.ArrayList;

/**
 * Class to represent a repeat unit sample, each repeat unit has one or more
 * samples.
 * 
 * @author eoc21
 * 
 */
public class RepeatUnitSample {
	private ArrayList<PolymerProperty> properties;
	private String reference;
	private String additives;
	private String id;

	public RepeatUnitSample() {

	}

	/**
	 * 
	 * @param idValue
	 *            - String representation of RepeatUnitSample id.
	 * @param properties
	 *            - ArrayList<PolymerProperty>, properties for this sample.
	 * @param reference
	 *            - String representation for the citation for this sample.
	 * @param additive
	 *            - String representation for additive if present.
	 */
	public RepeatUnitSample(final String idValue,
			final ArrayList<PolymerProperty> properties,
			final String reference, final String additive) {
		this.id = idValue;
		this.properties = properties;
		this.reference = reference;
		this.additives = additive;
	}

	/**
	 * 
	 * @return String representation of sample id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return ArrayList<PolymerProperty> properties associated with this
	 *         sample.
	 */
	public ArrayList<PolymerProperty> getProperties() {
		return properties;
	}

	/**
	 * 
	 * @return String representation of sample reference.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @return String representation of additives.
	 */
	public String getAdditives() {
		return additives;
	}

	/**
	 * 
	 * @param propertyValues
	 *            - ArrayList<PolymerProperty>, all properties associated with
	 *            this sample.
	 */
	public void setProperties(final ArrayList<PolymerProperty> propertyValues) {
		this.properties = propertyValues;
	}

	/**
	 * 
	 * @param ref
	 *            - String representation of the sample citation.
	 */
	public void setReference(final String ref) {
		this.reference = ref;
	}

	/**
	 * 
	 * @param additiveValue
	 *            - String representation of the additives.
	 */
	public void setAdditives(final String additiveValue) {
		this.additives = additiveValue;
	}

	/**
	 * 
	 * @param idValue
	 *            - String representation of sample id.
	 */
	public void setId(final String idValue) {
		this.id = idValue;
	}
}
