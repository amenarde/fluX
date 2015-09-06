package flu_predictor;

import java.util.*;
import java.io.*;

public class PatientData {
	public static void updatePatientData(Patient p, boolean hasFlu, float latitude, 
			float longitude, boolean hasSymptoms, boolean hasVaccine) {
		File out = new File("data/patient_data.csv");
		String c = ",";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(out, true));
			bw.write(p.age + c + p.week + c + p.state + c + hasFlu + c + 
					latitude + c + longitude + c + hasSymptoms + c + hasVaccine);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("error in handling patient data file");
		}
	}
}
