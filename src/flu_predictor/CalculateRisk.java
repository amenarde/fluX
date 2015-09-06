package flu_predictor;

public class CalculateRisk {
	public static float[] risk(){
		float score = DataProcessor.weekAgeStateScore[WindowedInterface.user.getWeek()][WindowedInterface.user.getAge()][WindowedInterface.user.getState()];
		float zScore[] = {(score - DataProcessor.mean)/DataProcessor.std, (score - DataProcessor.ageMean)/DataProcessor.ageStd};
		return zScore;
	}
}
