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
	 * This hashmap contains the source airport IATA and all it's possible destinations
	 */
	private Map<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
	
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
		ArrayList<String> destinations = new ArrayList<String>();
		routes.put(sourceIATA, destinations);
		
		routeOutput = findDestinations(sourceIATA, destinationIATA);
		
	}
	
	/**
	 * This method creates a hashmap of a source airport and all its possible destinations
	 * 
	 * @param source
	 */
	private String findDestinations(String sourceIATA, String destinationIATA) {
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			String routeLine = "";
			
			Airports sourceAirport = new Airports(sourceIATA);
			
			//Iterating through the lines in route.csv until the last
			while((routeLine = bRouteRead.readLine()) != null) {
				
				String sourceAirportCodes = routeLine.split(",")[2];     //Field storing the source airport codes				
				
				if (sourceAirportCodes.equals(sourceIATA) && routeLine.split(",")[4].equals(destinationIATA)) {
					
					String destinationAirportCode = routeLine.split(",")[4];     //Field storing the destination airport code
					
					//Calculating distance between airports
					double distance = findDistance(sourceAirport, new Airports(destinationAirportCode));
					
					String airlineID = routeLine.split(",")[1];     //Field storing airline ID for route between source and destination airport

					//Field storing the destination airport code 
					//+ routeLine.split(",")[7] + " | " This is number of stops
					String sourcedestinationRoute = "AirlineID: " + airlineID + " | " +  "DestinationAirportCode: " + destinationAirportCode + " | Stops: " + routeLine.split(",")[7] + " | Distance: " + Double.toString(distance) + "km";
					
					routeOutput = sourcedestinationRoute;
					
					return sourcedestinationRoute;
					
				}
				//This code runs if the destination airport is not among the direct 
				else if (sourceAirportCodes.equals(sourceIATA)) {
					
					String destinationAirportCode = routeLine.split(",")[4];     //Field storing the source airport code

					//Calculating distance between airports
					double distance = findDistance(sourceAirport, new Airports(destinationAirportCode));
					
					String airlineID = routeLine.split(",")[1];

					//Field storing the destination airport code 
					//+ routeLine.split(",")[7] + " | " This is number of stops
					String destinationAirport = destinationAirportCode + " | "  + airlineID + " | " + Double.toString(distance) + "km";
					
					//ArrayList of destinations for the specific source being updated
					ArrayList<String> newDestinations = routes.get(sourceIATA);  
					newDestinations.add(destinationAirport);
					
					routes.put(sourceIATA, newDestinations);
					
					//return findIndirectRoute();					
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
	
	/**
	 * This returns a route starting at the destination and ending at the source
	 * @param sourceIATA
	 * @param destinationIATA
	 * @return
	 */
	public String findSources(String sourceIATA, String destinationIATA) {
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			String routeLine = "";
			
			Airports destinationAirport = new Airports(destinationIATA);
			
			//Iterating through the lines in route.csv until the last
			while((routeLine = bRouteRead.readLine()) != null) {
				
				String destinationAirportCodes = routeLine.split(",")[2];     //Field storing the source destination codes				
				
				if (destinationAirportCodes.equals(destinationIATA) && routeLine.split(",")[4].equals(sourceIATA)) {
					
					String sourceAirportCode = routeLine.split(",")[4];     //Field storing the source airport code
					
					//Calculating distance between airports
					double distance = findDistance(destinationAirport, new Airports(sourceAirportCode));
					
					String airlineID = routeLine.split(",")[1];     //Field storing airline ID for route between source and destination airport

					//Field storing the destination airport code 
					//+ routeLine.split(",")[7] + " | " This is number of stops
					String sourcedestinationRoute = "AirlineID: " + airlineID + " | " +  "SourceAirportCode: " + sourceAirportCode + " | Stops: " + routeLine.split(",")[7] + " | Distance: " + Double.toString(distance) + "km";
					
					routeOutput = sourcedestinationRoute;
					
					return sourcedestinationRoute;
					
				}
				//This code runs if the source airport is not among the direct 
				else if (destinationAirportCodes.equals(destinationIATA)) {
					
					String sourceAirportCode = routeLine.split(",")[4];     //Field storing the source airport code

					//Calculating distance between airports
					double distance = findDistance(destinationAirport, new Airports(sourceAirportCode));
					
					String airlineID = routeLine.split(",")[1];

					//Field storing the source airport code 
					//+ routeLine.split(",")[7] + " | " This is number of stops
					String sourceAirport = sourceAirportCode + " | "  + airlineID + " | " + Double.toString(distance) + "km";
					
					/*
					//ArrayList of destinations for the specific source being updated
					ArrayList<String> newDestinations = routes.get(sourceIATA);  
					newDestinations.add(destinationAirport);
					
					routes.put(sourceIATA, newDestinations);
					
					//return findIndirectRoute();
					 * 					
					 */
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
		
		return Math.round(d*1000.0)/1000.0;
		
	}
	
	
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
		Routes r1 = new Routes("SVX", "NJC");
		System.out.println(r1.routes.toString());
		System.out.println(r1.getRouteOutput());
	}

}