package populatingontologies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to read in files and return mapped objects.
 * @author ed
 *
 */
public class Mapper {
	private File fileToMap;
	/**
	 * 
	 * @param f - File object to read mappings.
	 */
	public Mapper(final File f){
		this.fileToMap = f;
	}
	
	public ArrayList<PolyInfo2ChemAxiomPropertyMapper> mapProperties() throws IOException{
		ArrayList<PolyInfo2ChemAxiomPropertyMapper> mappedProperties = new ArrayList<PolyInfo2ChemAxiomPropertyMapper>();
		BufferedReader br = new BufferedReader(new FileReader(fileToMap));
		String line;
		while((line = br.readLine())!=null){
			String [] propertyInformation = line.split("\t");
			PolyInfo2ChemAxiomPropertyMapper propertyMapper = new PolyInfo2ChemAxiomPropertyMapper(propertyInformation[0],propertyInformation[1]);
			mappedProperties.add(propertyMapper);
		}
		return mappedProperties;
	}
}
