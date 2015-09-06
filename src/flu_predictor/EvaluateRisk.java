package flu_predictor;

public class EvaluateRisk {
	public static String[] advice(float[] risk){
		String[] advice = new String[2];
		
		if(risk[0] > 4){
			advice[0] = "Based on your age, location, and the time of year, you have a very high risk of catching the flu relative to the entire population.";
		}
		else if(risk[0] > 1){
			advice[0] = "Based on your age, location, and the time of year, you have a high risk of catching the flu relative to the entire population.";
		}
		else if(risk[0] > 0){
			advice[0] = "Based on your age, location, and the time of year, you have a moderate-high risk of catching the flu relative to the entire population.";
		}
		else if(risk[0] > -1){
			advice[0] = "Based on your age, location, and the time of year, you have a moderate-low risk of catching the flu relative to the entire population.";
		}
		else if(risk[0] > -4){
			advice[0] = "Based on your age, location, and the time of year, you have a low risk of catching the flu relative to the entire population.";
		}
		else{
			advice[0] = "Based on your age, location, and the time of year, you have a very low risk of catching the flu relative to the entire population.";
		}
////////////////////////////////////		
		if(risk[1] > 4){
			advice[1] = "Compared to others in your age group you have a very high risk of catching the flu.";
		}
		else if(risk[1] > 1){
			advice[1] = "Compared to others in your age group you have a high risk of catching the flu.";
		}
		else if(risk[1] > 0){
			advice[1] = "Compared to others in your age group you have a moderate-high risk of catching the flu.";
		}
		else if(risk[1] > -1){
			advice[1] = "Compared to others in your age group you have a moderate-low risk of catching the flu.";
		}
		else if(risk[1] > -4){
			advice[1] = "Compared to others in your age group you have a low risk of catching the flu.";
		}
		else{
			advice[1] = "Compared to others in your age group you have a very low risk of catching the flu.";
		}
		
		return advice;
		
	}
}
