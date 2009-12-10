package xmlextraction;

import java.util.ArrayList;

/**
 * Class to represent a polymer property information container.
 * 
 * @author eoc21
 * 
 */
public class PolymerProperty {
	private String hasName = "";
	private String hasUnit = "";
	private String hasValue = "";
	private String hasTechnique = "";
	private String hasCondition = "";
	private String hasRemark = "";
	private String hasId = "";
	private ArrayList<DataProperty> dataProperties;

	public PolymerProperty() {

	}

	public PolymerProperty(final String name, final String unit,
			final String value, final String technique, final String condition,
			final String remark) {
		this.hasName = name;
		this.hasUnit = unit;
		this.hasValue = value;
		this.hasTechnique = technique;
		this.hasCondition = condition;
		this.hasRemark = remark;
	}

	/**
	 * 
	 * @return String representation of polymer property name.
	 */
	public String getName() {
		return hasName;
	}

	/**
	 * 
	 * @return String representation of the unit associated with the property.
	 */
	public String getUnit() {
		return hasUnit;
	}

	/**
	 * 
	 * @return String representation of the value associated with the property.
	 */
	public String getValue() {
		return hasValue;
	}

	/**
	 * 
	 * @return String representation of the measurement technique associated
	 *         with the property.
	 */
	public String getTechnique() {
		return hasTechnique;
	}

	/**
	 * 
	 * @return String representation of the conditions associated with the
	 *         measurement technique / property.
	 */
	public String getCondition() {
		return hasCondition;
	}

	/**
	 * 
	 * @return String representation of any remarks about the property.
	 */
	public String getRemark() {
		return hasRemark;
	}

	/**
	 * 
	 * @return String representation of the polymer property id.
	 */
	public String getId() {
		return hasId;
	}

	/**
	 * 
	 * @return ArrayList<DataProperty>, all data associated with this property.
	 */
	public ArrayList<DataProperty> getDataProperties() {
		return dataProperties;
	}

	/**
	 * 
	 * @param aName
	 *            - String representation of the property name.
	 */
	public void setName(final String aName) {
		this.hasName = aName;
	}

	/**
	 * 
	 * @param aUnit
	 *            - String representation of the propertie's unit.
	 */
	public void setUnit(final String aUnit) {
		this.hasUnit = aUnit;
	}

	/**
	 * 
	 * @param aValue
	 *            - String representation of the value associated with the
	 *            property.
	 */
	public void setValue(final String aValue) {
		this.hasValue = aValue;
	}

	/**
	 * 
	 * @param technique
	 *            - String representation of the technique associated with the
	 *            property.
	 */
	public void setTechnique(final String technique) {
		this.hasTechnique = technique;
	}

	/**
	 * 
	 * @param condition
	 *            - String representation of the condition associated with the
	 *            measurement technique / property.
	 */
	public void setCondition(final String condition) {
		this.hasCondition = condition;
	}

	/**
	 * 
	 * @param remark
	 *            - String representation of any associated remark.
	 */
	public void setRemark(final String remark) {
		this.hasRemark = remark;
	}

	/**
	 * 
	 * @param idValue
	 *            - String representation of the property id.
	 */
	public void setId(final String idValue) {
		this.hasId = idValue;
	}

	/**
	 * 
	 * @param data
	 *            - ArrayList<DataProperty>
	 */
	public void setDataProperties(ArrayList<DataProperty> data) {
		this.dataProperties = data;
	}
}
