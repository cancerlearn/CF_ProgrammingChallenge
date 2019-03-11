/**
 * 
 */
package albertKyei.CFPC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Albert Kyei & Goodie Dawson
 * @version 1.0
 *
 * This class handles all information of the airports and any interaction with said information.
 * Necessary data structures are created to optimize runtime of interactions with any information from the airport data.
 */
public class Airports {
	
	/**
	 * Latitude: measure of how far north or south of the equator a location is.
	 * This field stores the latitude of the airport.
	 */
	private double latitude;
	
	/**
	 * Longitude: measure of how far east or west of the prime meridian a location is.
	 * This field stores the longitude of the airport.
	 */
	private double longitude;
	
	/**
	 * This field contains the ID of the airport
	 */
	private String ID;
	
	/**
	 * Default constructor for this class.
	 * This constructor takes the IATA code as an argument to find and create the corresponding airport object.
	 * 
	 * @param ID
	 */
	public Airports(String ID) {
		
		this.ID = ID;
		
		BufferedReader bRead = null;
		
		try {
			
			bRead = new BufferedReader(new FileReader("airports.csv"));
			
			String line = "";
			
			while((line = bRead.readLine()) != null) {
				
				String sourceAirportIDinFile = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0];
				
				if (ID.equals(sourceAirportIDinFile)) {
					
					this.latitude = Double.parseDouble(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[6]);
					
					this.longitude = Double.parseDouble(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[7]);
					
				}
				
			}
			
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
		
	}
	
	/**
	 * Secondary constructor.
	 * Used when latitude and longitude are known at intialization of the object.
	 * 
	 * @param IATA
	 * @param latitude
	 * @param longitude
	 */
	public Airports(String ID, double latitude, double longitude) {
		
		this.ID = ID;
		this.latitude = latitude;
		this.longitude = longitude;
		
		
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the iATA
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iATA the iATA to set
	 */
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * This method returns an a list of all valid airport's IATAs in a given city and country.
	 * 
	 * @param city
	 * @param country
	 * @return ArrayList of airport IATAs
	 */
	protected static ArrayList<String> findAirportCodes(String city, String country) {
		
		BufferedReader bAirportRead = null;
		ArrayList<String> airportCodes = new ArrayList<String>();
		
		try {
			
			bAirportRead = new BufferedReader(new FileReader("airports.csv"));
			
			String airports = "";
			//Iterating through the lines until the last
			while((airports = bAirportRead.readLine()) != null) {
				
				String airportCity = airports.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2].replaceAll("\"", "");
				
				String airportCountry = airports.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[3].replaceAll("\"", "");
				
				String airportIATA = airports.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[4].replaceAll("\"", "");
				
				String airportID = airports.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0];
				
				//An airport is determined as valid if:
				//a. It's country and city exist in the given airports.csv file
				//b. It has a valid (not '\N') IATA
				//c. it is "route valid". Check the javadoc for the airportIDRouteValid() method for more info on this.
				if (city.equals(airportCity) && country.equals(airportCountry) && !airportIATA.equals("\\N") && airportIDRouteValid(airportID)){
					
					airportCodes.add(airports.split(",")[0]);
					
					
				}
			}
			
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
			try {
				if(bAirportRead != null) bAirportRead.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		
		}
		
		return airportCodes;
	
	}
	
	/**
	 * This method is used to determine if an airport's ID is "route valid", in other words, if there are any recorded routes including that airport in the routes.csv file
	 * 
	 * @param aID
	 * @return
	 */
	private static boolean airportIDRouteValid(String aID) {
		
		BufferedReader bRouteRead = null;
		
		try {
			
			bRouteRead = new BufferedReader(new FileReader("routes.csv"));
			
			String routes = "";
			//Iterating through the lines until the last
			while((routes = bRouteRead.readLine()) != null) {
				
				String airportIDinRouteFile = routes.split(",")[3];
				
				if (airportIDinRouteFile.equals(aID)) {
					
					return true;
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
	
		return false;
	}

}


