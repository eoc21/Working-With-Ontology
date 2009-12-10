package populatingontologies;
/**
 * RDF triple (subject-predicate-object).
 * @author eoc21
 *
 */
public class RDFTriple {
	private String subject;
	private String predicate;
	private String object;
	
	public RDFTriple(){
		
	}
	/**
	 * 
	 * @param subjectValue - String representation of Subject in RDF triple.
	 * @param predicateValue - String representation of Predicate in RDF triple.
	 * @param objectValue - String representation of Object in RDF triple.
	 */
	public RDFTriple(final String subjectValue, final String predicateValue, final String objectValue){
		this.subject = subjectValue;
		this.predicate = predicateValue;
		this.object = objectValue;
	}
	/**
	 * 
	 * @param sub - - String representation of Subject in RDF triple.
	 */
	public void setSubject(final String sub){
		this.subject = sub;
	}
	/**
	 * 
	 * @param pred - String representation of Predicate in RDF triple.
	 */
	public void setPredicate(final String pred){
		this.predicate = pred;
	}
	/**
	 * 
	 * @param obj - - String representation of Object in RDF triple.
	 */
	public void setObject(final String obj){
		this.object = obj;
	}
	/**
	 * 
	 * @return - String representation of Subject in RDF triple.
	 */
	public String getSubject(){
		return subject;
	}
	/**
	 * 
	 * @return - String representation of Predicate in RDF triple.
	 */
	public String getPredicate(){
		return predicate;
	}
	/**
	 * 
	 * @return - String representation of Object in RDF triple.
	 */
	public String getObject(){
		return object;
	}

}
