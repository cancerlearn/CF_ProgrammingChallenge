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
	

	private String routeOutput;
	
	private String directRoute;
	
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
		
		directRoute = "";
		
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
					
					System.out.println(airportIATAInFIle);
					//Calculating distance between airports
					double distance = findDistance(sourceAirport, new Airports(destinationAirportIATA));
					
					
					String routeToDestination = "AirlineID: " + airlineID + ", DestinationAirportCode: " + destinationAirportIATA + ", Stops: " + routeLine.split(",")[7] + 
							", Distance: " + Double.toString(distance) + "km";
					
					
					//ArrayList of destinations for the specific source being updated
					destinations.add(routeToDestination);
					
					if (airportIATAInFIle.equals(sourceIATA) && destinationAirportIATA.equals(destinationIATA)) { 
						
						System.out.println("End of findDestinations(): "+System.currentTimeMillis()); 
						
						directRoute = routeToDestination;
						
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
		
		return findIndirectRoute(destinations, destinationIATA);

	}
	
	
	public String findIndirectRoute(ArrayList<String> possibleDestinations, String mainDestination) {
		System.out.println("Here they are: "+possibleDestinations.toString());
		for (String possibleDestination: possibleDestinations) {
			
			Routes r1 = new Routes(possibleDestination, mainDestination);
			
			if (!(r1.getDirectRoute().equals(""))) {
				
				return r1.getRouteOutput();
				
			}
			
			return possibleDestination + " TO " + r1.getRouteOutput();
			
		}
		
		return "Unsupported Request!";
		
	}
	
	
	/**
	 * This function uses the harvesine function to calculate the distance between two airports.
	 * @param a1
	 * @param a2
	 * @return the distance
	 */
	private double findDistance(Airports a1, Airports a2) {
		
		//System.out.println("Start of findDistance(): "+System.currentTimeMillis());
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
		
		//System.out.println("End of findDistance(): "+System.currentTimeMillis());
		return Math.round(d*1000.0)/1000.0;
		
	}
	
	//--------------------------- End of Auxiliary methods ---------------------------

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
	}

	//--------------------------- Start of Getters ---------------------------
	
	/**
	 * @return the routeOutput
	 */
	public String getRouteOutput() {
		return routeOutput;
	}


	/**
	 * @return the directRoute
	 */
	public String getDirectRoute() {
		return directRoute;
	}

	//--------------------------- End of Getters ---------------------------

	public static void main(String[]args) {

		Routes r1 = new Routes("SVX", "NBC");
		System.out.println(r1.destinations.toString());

		Routes r1 = new Routes("VNS", "DYU");
		//System.out.println(r1.destinations.toString());

		System.out.println(r1.getRouteOutput());
	}

}