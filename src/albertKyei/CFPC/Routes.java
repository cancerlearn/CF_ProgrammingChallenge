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
 * @author 
 *
 */
public class Routes {
	
	/**
	 * This ArrayList contains the source airport's possible destinations
	 */
	private ArrayList<String> destinations = new ArrayList<String>();
	
	private ArrayList<String> sources = new ArrayList<String>();
	//private Map<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
	
	
	private String routeOutput;
	
	
	/**
	 * Default constructor
	 * 
	 * @param sourceIATA
	 * @param destinationIATA
	 */
	public Routes(String sourceIATA, String destinationIATA) {
		
		/**
		 * ArrayList containing all possible destinations from the source given
		 */
		//ArrayList<String> destinations = new ArrayList<String>();
		//routes.put(sourceIATA, destinations);
		
		Airlines a1 = new Airlines();
		routeOutput = findDestinations(sourceIATA, destinationIATA, a1);
		
	}
	
	
	//--------------------------- Start of Auxiliary methods ---------------------------
	
	/**
	 * This method creates a hashmap of a source airport and all its possible destinations
	 * 
	 * @param source
	 */
	private String findDestinations(String sourceIATA, String destinationIATA, Airlines a1) {
		
		System.out.println("Start of findDestinations(): "+System.currentTimeMillis());
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			
			String routeLine = "";
			
			Airports sourceAirport = new Airports(sourceIATA);
			
			//This while loop is intended to find a match of the destination among the source airport's list of possible destination
			//If found the right output string is returned
			//Else, the loop keeps running and storing all the source's possible destinations
			while((routeLine = bRouteRead.readLine()) != null) {
				
				String airportIATAInFIle = routeLine.split(",")[2];     //Field storing the source airport codes
				
				String destinationAirportIATA = routeLine.split(",")[4];
				
				String airlineID = routeLine.split(",")[1];

				//If the airline is active
				if (airportIATAInFIle.equals(sourceIATA) && a1.getAirlineActive(airlineID)) {
					
					
					//Calculating distance between airports
					double distance = findDistance(sourceAirport, new Airports(destinationAirportIATA));
					
					
					String routeToDestination = "AirlineID: " + airlineID + ", DestinationAirportCode: " + destinationAirportIATA + ", Stops: " + routeLine.split(",")[7] + 
							", Distance: " + Double.toString(distance) + "km";
					
					
					//ArrayList of destinations for the specific source being updated
					destinations.add(routeToDestination);
					
					if (airportIATAInFIle.equals(sourceIATA) && destinationAirportIATA.equals(destinationIATA)) { 
						//Take into account different airlines for one route?
						System.out.println("End of findDestinations(): "+System.currentTimeMillis()); 
						
						return routeToDestination;
					}
				
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
		System.out.println("End of findDestinations(): "+System.currentTimeMillis());
		return "";
	}
	
	/**
	 * This returns a route starting at the destination and ending at the source
	 * @param sourceIATA
	 * @param destinationIATA
	 * @return
	 */
	public String findSources(String sourceIATA, String destinationIATA, Airlines a1) {
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			
			String routeLine = "";
			
			Airports destinationAirport = new Airports(destinationIATA);
			
			//Iterating through the lines in route.csv until the last
			while((routeLine = bRouteRead.readLine()) != null) {
				
				String airportIATAInFIle = routeLine.split(",")[4];     //Field storing the destination airport code
				
				String sourceAirportIATA = routeLine.split(",")[2];     //Field storing the source airport codes
				
				String airlineID = routeLine.split(",")[1];

				//If the airline is active and the destinationIATA is the desired one
				if (airportIATAInFIle.equals(destinationIATA) && a1.getAirlineActive(airlineID)) {
					
					
					//Calculating distance between airports
					double distance = findDistance(destinationAirport, new Airports(sourceAirportIATA));
					
					
					String routeFromSource = "AirlineID: " + airlineID + ", SourceAirportCode: " + sourceAirportIATA + ", Stops: " + routeLine.split(",")[7] + 
							", Distance: " + Double.toString(distance) + "km";
					
					
					//ArrayList of destinations for the specific source being updated
					sources.add(routeFromSource);
					
					if (airportIATAInFIle.equals(destinationIATA) && sourceAirportIATA.equals(sourceIATA)) { 
						//Take into account different airlines for one route?
						System.out.println("End of findDestinations(): "+System.currentTimeMillis()); 
						
						return routeFromSource;
					}
				
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
		
		return "";
		
	}
	
	public String findIndirectRoute() {
		
		
		
	}
	
	
	/**
	 * This function uses the harvesine function to calculate the distance between two airports.
	 * @param a1
	 * @param a2
	 * @return the distance
	 */
	private double findDistance(Airports a1, Airports a2) {
		
		System.out.println("Start of findDistance(): "+System.currentTimeMillis());
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
		
		System.out.println("End of findDistance(): "+System.currentTimeMillis());
		return Math.round(d*1000.0)/1000.0;
		
	}
	
	//--------------------------- End of Auxiliary methods ---------------------------
	
/*	public String getAllRoutes(String start, String end) {
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
	}*/

	/**
	 * @return the routeOutput
	 */
	public String getRouteOutput() {
		return routeOutput;
	}

	public static void main(String[]args) {
		Routes r1 = new Routes("SVX", "YWG");
		System.out.println(r1.destinations.toString());
		System.out.println(r1.getRouteOutput());
	}

}