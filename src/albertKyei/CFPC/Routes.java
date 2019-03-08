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
	
	/**
	 * This function uses the harvesine function to calculate the distance between two airports.
	 * @param a1
	 * @param a2
	 * @return the distance
	 */
	private double findDistance(Airports a1, Airports a2) {
		
		//Instance variables for harvesine formula, where r is the earths radius in km
		int r = 6731;
		double lat1 = Math.toRadians(a1.getLatitude());
		double lat2 = Math.toRadians(a2.getLatitude());
		double lon1 = Math.toRadians(a1.getLongitude());
		double lon2 = Math.toRadians(a2.getLongitude());
		
		double latdif = lat2 - lat1;
		double londif = lon2 - lon1;
		
		//Harvesine calculation
		double a = Math.sin(latdif/2) * Math.sin(latdif/2) + Math.cos(lat1) * Math.cos(lat2) *
		        Math.sin(londif/2) * Math.sin(londif/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = r * c;
		
		return d;
		
	}
	
	public String getAllRoutes(String start, String end) {
		String output = "";
		int flights = 0;
		
		BufferedReader br = null;
		
		try {
			
			//First step: check whether the final destination is among the list of all destinations
			//Second step: calculate distance and return output if it is. else go to step three
			//Third step: find all source destinations which lead to final destination
			//Fourth step: find any matches among the main source's list of destinations and the final destination's list of possible source
			//Fifth step: carry this out recursively until a match is found
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		}
		
		return output;
	}

	public static void main(String[]args) {
		Routes r1 = new Routes("SVX");
		System.out.println(r1.routes.toString());
	}

}