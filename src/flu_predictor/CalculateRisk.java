package flu_predictor;

public class CalculateRisk {
	public static float risk(){
		float score = DataProcessor.weekAgeStateScore[WindowedInterface.user.getWeek()][WindowedInterface.user.getAge()][WindowedInterface.user.getState()];
		float xScore = (score - DataProcessor.mean)/DataProcessor.std;
		return xScore;
		
	}
}
