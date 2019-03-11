/**
 * 
 */
package albertKyei.CFPC;
import albertKyei.CFPC.Airports;
import albertKyei.CFPC.Routes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * @author 
 * @version 1.0
 * 
 * This class is the center of attention where everything happens.
 * The input file is read from here, and all necessary methods are run from here.
 * The output file is also written to from this class.
 */
public class FlightFinder {
	
	private static Airlines airlines = new Airlines();
	
	private static String desiredDestination = "";
	
	/**
	 * This method reads from the input file and returns the starting and ending point as an array.
	 * 
	 * @return array containing starting and ending "city, country"
	 */
	private static String[] getInputTxt(String file) {
		
		BufferedReader bRead = null;
		String[] input = new String[2];
		
		try {
			
			bRead = new BufferedReader(new FileReader(file));
			input[0] = bRead.readLine();
			if (input[0] == null) System.out.println("Input file empty.");
			input[1] = bRead.readLine();
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
			try {
				if(bRead != null) bRead.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
		return input;
		
	}
	
	private static void writeOutputTxt(String outTxt) {
		
		BufferedWriter bWrite = null;
		
		@SuppressWarnings("unused")
		String finalOutput = "";
		
		try {
			
			bWrite = new BufferedWriter(new FileWriter("destinationfrom-X-to-Y_output.txt"));
			
			String[] points = outTxt.split(" --- To --- ");
			
			int numberOfFlights = 0;
			
			int totalStops = 0;
			
			double totalDistance = 0.0;
			
			String line = "";
			
			for (String point: points) {
				//System.out.println(point+" Point\n");
				numberOfFlights++;
				
				String airlineID = point.split(",")[0].split(": ")[1];
				line += airlineID + " from ";
				
				String sourceAirportIATA = point.split(",")[1].split(": ")[1].split("_")[0];
				line += sourceAirportIATA + " to ";
				
				String destinationAirportIATA = point.split(",")[2].split(": ")[1].split("_")[0];
				line += destinationAirportIATA;
				
				String stops = point.split(",")[3].split(": ")[1];
				totalStops += Integer.parseInt(stops);
				line += " Stops " + stops + "\n";
				
				String sourceAirportID = point.split(",")[1].split(": ")[1].split("_")[1];
				
				String destinationAirportID = point.split(",")[2].split(": ")[1].split("_")[1];
				
				totalDistance += findDistance(new Airports(sourceAirportID), new Airports(destinationAirportID));
				
			}
			
			finalOutput += line + "\nTotal flights: " + numberOfFlights + 
					"\nTotal additional stops: " + totalStops + 
					"\nTotal distance: " + (Math.round(totalDistance*1000.0)/1000.0) + "km\n" + 
					"Optimality criteria: flights";
			
			bWrite.write(finalOutput);
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("Output file written to does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
			try {
				if(bWrite != null) bWrite.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
	}
	
	
	/**
	 * This method finds the indirect route between two airports.
	 * It is recursive and utilizes the Routes class to find this route.
	 * The first route found is returned.
	 * 
	 * @param possibleDestinations
	 * @param parentDestination
	 * @param path
	 * @param level
	 * @return
	 */
	private static String findIndirectRoute(ArrayList<String> possibleDestinations, String parentDestination, ArrayList<String> path, int level) {
		
		Routes r1 = null;
		
		String intermediaryDestination = null;
		

		for (String currentDestination: possibleDestinations) {
			
			//Intermediary destination between main source and main destination is obtained
			intermediaryDestination = currentDestination.split(",")[2].split(": ")[1].split("_")[1];
			
			//Adding new intermediary to path array
			if (!path.contains(intermediaryDestination)) path.add(intermediaryDestination);
			else continue;
			
			//New route is created between the intermediary and main destination
			r1 = new Routes(FlightFinder.airlines, intermediaryDestination, FlightFinder.desiredDestination, path);
			
			//If a direct route is obtained from the Routes object created above
			if (!(r1.getDirectRoute().equals("No"))) {
				
				return currentDestination + " --- To --- " + r1.getRouteOutput();
				
			}
			
			
			if (level == 1) continue;
			
			String next = findIndirectRoute(r1.getDestinations(), currentDestination, path, level - 1);
			
			if(next.contains("Unsupported Request")) {
				
				path.remove(path.size()-1);
				
				continue;
			}
			
			return  currentDestination + " --- To --- " + next;
			
				
			}
		
		return "Unsupported Request";
		
	}

	/**
	 * This function uses the harvesine function to calculate the distance between two airports.
	 * @param a1
	 * @param a2
	 * @return the distance
	 */
	private static double findDistance(Airports a1, Airports a2) {
		
		//System.out.println("Start of findDistance(): "+System.currentTimeMillis());
		//Instance variables for harvesine formula, where r is the earths radius in km
		double r = 6371;
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
		return d;
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Start: "+System.currentTimeMillis());

		//Store starting point and destination
		String[] inputTxt = getInputTxt(args[0]);

		//These two fields store the city and country of source
		String sourceCity = inputTxt[0].split(", ")[0];
		String sourceCountry = inputTxt[0].split(", ")[1];
		
		//These two fields store the city and country of the destination
		String destinationCity = inputTxt[1].split(", ")[0];
		String destinationCountry = inputTxt[1].split(", ")[1];
		
		//Find the source airports in given city and country
		ArrayList<String> allSourceAirportCodes = Airports.findAirportCodes(sourceCity, sourceCountry);
		
		//Find the destination airports in given city and country
		ArrayList<String> allDestinationAirportCodes = Airports.findAirportCodes(destinationCity, destinationCountry);
		
		//Create route object between initial and final destination
		Routes route1 = null;
		
		try {
			
			route1 = new Routes(FlightFinder.airlines, allSourceAirportCodes.get(0), allDestinationAirportCodes.get(0), new ArrayList<String>());
			
		} catch(IndexOutOfBoundsException iobe) {
			
			System.out.println("Airport for source or destination does not exist.\nUnsupported Request.");
			System.exit(0);
		}
		
		
		//static variable is assigned to final destination
		//This helps to avoid passing an extra argument in the recursive function 'findIndirectRoute()'.
		FlightFinder.desiredDestination = route1.getDestinationID();
		
		//If a direct route exists between starting and ending point, it is determined at initialization.
		//And the getter for the field 'directRoute' is used to verify this direct route does not exit.
		if (route1.getDirectRoute().equals("No")) {
			
			/**
			 * This field represents the number of hops allowed when searching for an indirect route.
			 * It is a 'hop limit' for flights.
			 */
			int flightLimit = 1;
			
			//The routeOutput of the 'route1' object created above is set to the result of finding the indirect route
			route1.setRouteOutput(findIndirectRoute(route1.getDestinations(), route1.getSourceID(), new ArrayList<String>(), 1));
			
			//This while loop is used to search for an indirect route at varying 'hop limits'
			while (route1.getRouteOutput().equals("Unsupported Request")) {
				
				flightLimit++;
				
				route1.setRouteOutput(findIndirectRoute(route1.getDestinations(), route1.getSourceID(), new ArrayList<String>(), flightLimit));
				
				//The indirect route search is stopped at 5
				//This can be changed, but runtime is sure to increase if no direct route exists.
				if (flightLimit >= 5) break;
			}
		
		}
		
		writeOutputTxt(route1.getRouteOutput());
		
		System.out.println("End: "+System.currentTimeMillis());
		
	}
	
	

}
