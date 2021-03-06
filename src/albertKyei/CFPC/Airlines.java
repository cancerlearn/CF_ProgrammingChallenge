/**
 * 
 */
package albertKyei.CFPC;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Albert Kyei & Goodie Dawson
 * @version 1.0
 *
 */
public class Airlines {
	
	
	private Map<String, Boolean> actives = new HashMap<String, Boolean>();
	
	

	/**
	 * Default constructor
	 * 
	 * @param airlineID
	 */
	public Airlines () {
		
		findActives();
		
	}
	
	//---------------------------Auxiliary method
	
	private void findActives() {
		
		BufferedReader readAirlines = null;
		
		try {
			
			readAirlines = new BufferedReader(new FileReader("airlines.csv"));
			String lines = "";
			
			while ((lines = readAirlines.readLine()) !=null) {
				
				String airlineIDInFile = lines.split(",")[0];
				String airlineStatus = lines.split(",")[7].replaceAll("\"", "");
				
				if (airlineStatus.equals("Y")) actives.put(airlineIDInFile, true);
				
			}
	
		} catch(FileNotFoundException fnfe) {
		
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
		
		} catch(IOException ioe) {
		
			ioe.printStackTrace();
			
		} finally {
			
			try {
				
				if(readAirlines != null) readAirlines.close();
				
			} catch(IOException ioe) {
				
				ioe.printStackTrace();
			
			}
			
		}
		
	}
	
	//---------------------------Getter

	/**
	 * @return the airlineActive
	 */
	public Boolean getAirlineActive(String id) {
		return actives.containsKey(id);
	}
	
	
}
