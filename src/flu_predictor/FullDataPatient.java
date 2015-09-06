package flu_predictor;

public class FullDataPatient {

	int age;
	int week;
	int state;
	boolean hasFlu;
	float lat;
	float lon;
	boolean hasSymptoms;
	boolean hasVaccine;
	
	public FullDataPatient(int age, int week, int state, boolean hasFlu, float lat, float lon,
			boolean hasSymptoms, boolean hasVaccine) {
		this.age = age;
		this.week = week;
		this.state = state;
		this.hasFlu = hasFlu;
		this.lat = lat;
		this.lon = lon;
		this.hasSymptoms = hasSymptoms;
		this.hasVaccine = hasVaccine;
	}
}
