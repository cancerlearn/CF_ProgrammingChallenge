/**
 * 
 */
package albertKyei.CFPC;
import albertKyei.CFPC.Airports;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @author Albert Kyei
 *
 */
public class Routes {
	
	/**
	 * 
	 */
	private Map<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
	
	public Routes(String source) {
		
		/**
		 * ArrayList containing all possible destinations from the source given
		 */
		ArrayList<String> destinations = new ArrayList<String>();
		routes.put(source, destinations);
		
		fillRoutes(source);
		
	}
	
	/**
	 * This method creates a hashmap of a source airport and all its possible destinations
	 * 
	 * @param source
	 */
	private void fillRoutes(String source) {
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			String routeLine = "";
			//Iterating through the lines until the last
			while((routeLine = bRouteRead.readLine()) != null) {
				
				//Field storing the source airport code
				String sourceAirportCode = routeLine.split(",")[2];
				
				//Calculating distance between airports
				double distance = findDistance(new Airports(sourceAirportCode), new Airports(routeLine.split(",")[4]));
				
				//Field storing the destination airport code
				String destinationAirportCode = routeLine.split(",")[4] + " | " + routeLine.split(",")[7] + " | " + routeLine.split(",")[1] + " | " + Double.toString(distance);
				
				if (sourceAirportCode.equals(source)) {
					
					//ArrayList of destinations for the specific source being updated
					ArrayList<String> newDestinations = routes.get(source);
					newDestinations.add(destinationAirportCode);
					
					routes.put(source, newDestinations);
					
				}
				
			}
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
			try {
				if(bRouteRead != null) bRouteRead.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
	}
	
	private double findDistance(Airports a1, Airports a2) {
		
		
		
	}
	
	public static void main(String[]args) {
		Routes r1 = new Routes("SVX");
		System.out.println(r1.routes.toString());
	}

}