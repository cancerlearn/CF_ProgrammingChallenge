/**
 * 
 */
package albertKyei.CFPC;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @author Albert Kyei & Goodie Dawson
 * @version 1.0
 *
 */
public class Routes {
	
	/**
	 * This ArrayList contains the source airport's possible destinations
	 */
	private ArrayList<String> destinations = new ArrayList<String>();
	
	private String routeOutput;
	
	private String directRoute;
	
	private Airlines a1;
	
	private String airlineID;
	
	private String sourceID;
	
	private String sourceIATA;
	
	private String destinationID;
	
	private String destinationIATA;
	
	private String stops;
	
	@SuppressWarnings("unused")
	private ArrayList<String> previousRoutes;
	
	/**
	 * Default constructor
	 * 
	 * 
	 * @param a1
	 * @param sourceID
	 * @param destinationID
	 * @param previousRoute
	 */
	public Routes(Airlines a1, String sourceID, String destinationID, ArrayList<String> previousRoute) {
		
		directRoute = "No";
		
		this.a1 = a1;
		
		this.sourceID = sourceID;
		
		this.destinationID = destinationID;
		
		this.previousRoutes = new ArrayList<String>();
		
		routeOutput = findDirectDestination(sourceID, destinationID, a1);
		
	}
	
	
	//--------------------------- Start of Auxiliary methods ---------------------------
	
	/**
	 * This method finds a direct route between a source and destination airport
	 * If it does not exist, the simultaneous creation of possible destinations from
	 * the source airport is utilized in finding an indirect route
	 * 
	 * @param sourceID
	 * @param destinationID
	 * @param a1
	 * @return
	 */
	private String findDirectDestination(String sourceID, String destinationID, Airlines a1) {
		
		//System.out.println("Start of findDestinations(): "+System.currentTimeMillis());
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			
			String routeLine = "";
			
			//This while loop is intended to find a match of the destination among the source airport's list of possible destination
			//If found the right output string is returned
			//Else, the loop keeps running and storing all the source's possible destinations
			while((routeLine = bRouteRead.readLine()) != null) {
				
				String sourceAirportIDInFIle = routeLine.split(",")[3];     //Field storing the source airport codes
				
				String destinationAirportID = routeLine.split(",")[5];     //Field storing the destination airport codes
				
				String airlineID = routeLine.split(",")[1];     //Field storing the airline IDs
				
				//If the airline is active
				if (sourceAirportIDInFIle.equals(sourceID) && a1.getAirlineActive(airlineID)) {
					
					String sourceIATA = routeLine.split(",")[2];     //Field storing the source airport IATAs
					
					String destinationIATA = routeLine.split(",")[4];     //Field storing the destination airport IATAs
					
					String stops = routeLine.split(",")[7];
					
					String routeToDestination = "AirlineID: " + airlineID + ", from SourceAirportIATA: " + sourceIATA + "_" + sourceAirportIDInFIle + 
							", DestinationAirportIATA: " + destinationIATA + "_" + destinationAirportID + 
							", Stops: " + stops;
					
					//ArrayList of destinations for the specific source being updated
					destinations.add(routeToDestination);
					
					if (sourceAirportIDInFIle.equals(sourceID) && destinationAirportID.equals(destinationID)) { 
						
						//System.out.println("End of findDestinations(): "+System.currentTimeMillis());

						this.sourceIATA = sourceIATA;
						
						this.destinationIATA = destinationIATA;
						
						this.stops = stops;
						
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
		//System.out.println("End of findDestinations(): "+System.currentTimeMillis());

		return "";
	}	
	
	
	//--------------------------- End of Auxiliary methods ---------------------------


	
	//--------------------------- Start of Getters & Setters ---------------------------
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirlineID: " + this.airlineID + ", from SourceAirportIATA: " + this.sourceIATA + "_" + this.sourceID + 
				", DestinationAirportIATA: " + destinationIATA + "_" + this.destinationID + 
				", Stops: " + this.stops;
	}


	/**
	 * @return the routeOutput
	 */
	public String getRouteOutput() {
		return routeOutput;
	}
	
	/**
	 * @return the sourceIATA
	 */
	public String getSourceID() {
		return sourceID;
	}


	/**
	 * @return the destinationIATA
	 */
	public String getDestinationID() {
		return destinationID;
	}


	/**
	 * @param routeOutput the routeOutput to set
	 */
	public void setRouteOutput(String routeOutput) {
		this.routeOutput = routeOutput;
	}


	/**
	 * @return the destinations
	 */
	public ArrayList<String> getDestinations() {
		return destinations;
	}


	/**
	 * @return the directRoute
	 */
	public String getDirectRoute() {
		return directRoute;
	}

	//--------------------------- End of Getters % Setters ---------------------------

}