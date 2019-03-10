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
	
	/**
	 * This method reads from the input file and returns the starting and ending point as an array.
	 * 
	 * @return array containing starting and ending "city, country"
	 */
	private static String[] getInputTxt() {
		
		BufferedReader bRead = null;
		String[] input = new String[2];
		
		try {
			
			bRead = new BufferedReader(new FileReader("destinationfrom-X-to-Y.txt"));
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
		
		try {
			
			bWrite = new BufferedWriter(new FileWriter("destinationfrom-X-to-Y_output.txt"));
			bWrite.write(outTxt);
			bWrite.newLine();
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Store starting point and destination
		String[] inputTxt = getInputTxt();


		String city = inputTxt[0].split(",")[0].replaceAll("\\s+", "");
		String country = inputTxt[0].split(",")[1].replaceAll("\\s+", "");
		
		//Find the airports in given city and country
		ArrayList<String> allAirportIATAs = Airports.findAirportIATA("\""+city+"\"","\""+country+"\"");
		System.out.println(allAirportIATAs.toString());
		
		//Airports a1 = new Airports();
		//Airports a2 = new Airports();
		
		//Routes r1 = new Routes();
		
		//writeOutputTxt(r1.getAllRoutes());

	}

}
