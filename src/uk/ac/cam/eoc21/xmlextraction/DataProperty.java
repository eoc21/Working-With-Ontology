package xmlextraction;

/**
 * Class to address the Data tags under the <Property> tag.
 * 
 * @author eoc21
 * 
 */
public class DataProperty {
	private String id;
	private String value;

	public DataProperty() {

	}
	/**
	 * 
	 * @param idValue - String representation of <Data> id.
	 * @param dataValue - String representation of <Data> value.
	 */
	public DataProperty(final String idValue, final String dataValue) {
		this.id = idValue;
		this.value = dataValue;
	}
	/**
	 * 
	 * @return String representation of the DataProperty id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @return String representation of the value of the DataProperty.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 
	 * @param anId - String representation of DataProperty id.
	 */
	public void setId(final String anId) {
		this.id = anId;
	}
	/**
	 * 
	 * @param val - String representation of DataProperty value.
	 */
	public void setValue(final String val) {
		this.value = val;
	}
}
