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
	 * Using the city and country parameters, the right airport(s) is(are) found and the airport object(s) is(are) created.
	 * 
	 * @param city
	 * @param country
	 */
	public Airports(String city, String country) {
		
		
		
	}
	
	/**
	 * Secondary constructor.
	 * This constructor takes the IATA code as an argument to find and create the corresponding airport object.
	 * 
	 * @param IATA
	 */
	public Airports(String IATA) {
		
		
		
	}
	
	private String findAirportIATA() {
		
	}
	
	

}
