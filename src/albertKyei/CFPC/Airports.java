/**
 * 
 */
package albertKyei.CFPC;
import albertKyei.CFPC.Airlines;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Albert Kyei
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
	 * This field contains the IATA (three-letter code designating airports) of the airport
	 */
	private String IATA;
	
	/**
	 * Default constructor for this class.
	 * This constructor takes the IATA code as an argument to find and create the corresponding airport object.
	 * 
	 * @param IATA
	 */
	
	
	public Airports(String IATA) {
		
		this.IATA = IATA;
		
		
		
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
	public String getIATA() {
		return IATA;
	}

	/**
	 * @param iATA the iATA to set
	 */
	public void setIATA(String iATA) {
		IATA = iATA;
	}

	/**
	 * This method returns an a list of all airport's IATAs in a given city and country
	 * 
	 * @param city
	 * @param country
	 * @return ArrayList of airport IATAs
	 */
	protected static ArrayList<String> findAirportIATA(String city, String country) {
		
		BufferedReader bAirportRead = null;
		ArrayList<String> iatas = new ArrayList<String>();
		
		try {
			
			bAirportRead = new BufferedReader(new FileReader("airports.csv"));
			
			String airports = "";
			//Iterating through the lines until the last
			while((airports = bAirportRead.readLine()) != null) {
				
				//These fields store the city and country of the airport
				String airportCity = airports.split(",")[2];
				String airportCountry = airports.split(",")[3];
				
				if (city.equals(airportCity) && country.equals(airportCountry)){
					
					iatas.add(airports.split(",")[4]);
					
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
		
		return iatas;
	
	}

}


