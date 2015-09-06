package flu_predictor;

import java.io.IOException;
import com.maxmind.geoip.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class LocationIndexer{
	
	public static String getIP(){
		String ip = "n/a";
		try {
			URL url = new URL("http://checkip.amazonaws.com/");
			Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));
			ip = sc.nextLine();
			sc.close();
		} catch (Exception e) {
			System.out.println("fluX needs an internet connection to find your location.");
		}
		return ip;

	}
	
	public static float[] latLong() throws IOException{
		LookupService cl;
			cl = new LookupService("data/GeoLiteCity.dat",
			LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
		Location location = cl.getLocation(getIP());
		float[] latLong = {location.latitude, location.longitude};
		return latLong;
	}
	
	public static void main(String[] args) throws IOException{
		float[] latLong = latLong();		
	}
}
