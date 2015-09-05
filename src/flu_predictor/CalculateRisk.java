package flu_predictor;

public class CalculateRisk {
	public static float risk(){
		float score = DataProcessor.weekAgeStateScore[Interface.user.getWeek()][Interface.user.getAge()][Interface.user.getState()];
		float xScore = (score - DataProcessor.mean)/DataProcessor.std;
		return xScore;
		
	}
}
