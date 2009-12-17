package populatingontologies;
/**
 * This class creates an object with the polyInfo property and the corresponding
 * ChemAxiomProp.owl property value, so that the properties can be mapped into the 
 * ontology.
 * @author ed
 *
 */
public class PolyInfo2ChemAxiomPropertyMapper {
	private String polyInfoPropertyValue;
	private String chemAxiomPropertyValue;
	/**
	 * 
	 * @param polyInfoProperty- String representation of a property from the polyInfo database.
	 * @param chemAxiomProperty - String representation of a property from the ChemAxiomProp.owl file.
	 */
	public PolyInfo2ChemAxiomPropertyMapper(final String polyInfoProperty, final String chemAxiomProperty){
		this.polyInfoPropertyValue = polyInfoProperty;
		this.chemAxiomPropertyValue = chemAxiomProperty;
	}
	/**
	 * 
	 * @return String representation of the polyInfo property.
	 */
	public String getPolyInfoProperty(){
		return polyInfoPropertyValue;
	}
	/**
	 * 
	 * @return String representation of the ChemAxiomProperty from the ontology.
	 */
	public String getChemAxiomPropertyValue(){
		return chemAxiomPropertyValue;
	}
}
