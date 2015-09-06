package flu_predictor;

import java.util.*;
import java.io.*;

public class OutbreakPotential {
	
	public static float dist(float lat1, float lon1, float lat2, float lon2) {
		if (lat1 == lat2 && lon1 == lon2) {
			return 0.0f;
		}
		double R = 6372.8; // In kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = (float) (Math.toRadians(lat1));
        lat2 = (float) (Math.toRadians(lat2));
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (float) (R * c);
	}
	
	public static LinkedList<Integer> assessOutbreaks(float[][] dists) {
		LinkedList<Integer> indicesToConsider = new LinkedList<Integer>();
		LinkedList<Integer> outbreakPatients = new LinkedList<Integer>();
		for (int i = 0; i < dists.length; i++) {
			indicesToConsider.add(i);
		}
		while (indicesToConsider.size() > 0) {
			int curr = indicesToConsider.get(0);
			int count = 0;
			float[][] distArray = getDistArray();
			for (int i = 0; i < indicesToConsider.size(); i++) {
				if (distArray[curr][i] < 10.0f) { // 10 kilometers
					count++;
				}
			}
			if (count >= 3) {
				outbreakPatients.add(curr);
			}
		}
		return outbreakPatients;
	}
	
	public static float[][] getDistArray() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("data/patient_data.csv")));
			LinkedList<FullDataPatient> patients = new LinkedList<FullDataPatient>();
			Scanner sc = new Scanner(br);
			while (sc.hasNextLine()) {
				Scanner ls = new Scanner(sc.nextLine()).useDelimiter(",");
				patients.add(new FullDataPatient(Integer.parseInt(ls.next()), Integer.parseInt(ls.next()), 
						Integer.parseInt(ls.next()), Boolean.parseBoolean(ls.next()), 
						Float.parseFloat(ls.next()), Float.parseFloat(ls.next()), 
						Boolean.parseBoolean(ls.next()), Boolean.parseBoolean(ls.next())));
			}
			int L = patients.size();
			float[][] dists = new float[L][L];
			for (int i = 0; i < L; i++) {
				for (int j = 0; j < L; j++) {
					if (i == j) {
						dists[i][j] = 0.0f;
					} else {
						dists[i][j] = dist(patients.get(i).lat, patients.get(i).lon, 
								patients.get(j).lat, patients.get(j).lon);
						
					}
				}
			}
			return dists;
		} catch (IOException e) {
			System.out.println("error in method \"getDistArray\"");
		}
		return null;
	}
}
