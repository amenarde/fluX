package flu_predictor;

import java.io.*;
import java.util.*;

public class DataProcessor {
	
	static float[][] weekAgeIncidence = new float[53][5]; // week, age (range): incidence
	static float[][] weekStateLevel = new float[53][53]; // week, state: level
	static float[][][] weekAgeStateScore = new float[53][5][53]; //week, age, state: score
	
	public static float mean;
	public static float std;
	
	public static void getData () {
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File("data/FluSurveillance.csv")));
			Scanner sc = new Scanner(br);
			for (int i = 0; i < 3; i++) { 
				sc.nextLine();
			}
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() == 0) {
					break;
				}
				@SuppressWarnings("resource")
				Scanner lineSc = new Scanner(line).useDelimiter(",");
				lineSc.next();
				lineSc.next();
				lineSc.next();
				int year = Integer.parseInt(lineSc.next());
				int age = -1;
				int week = Integer.parseInt(lineSc.next());
				String ageString = lineSc.next();
				if (ageString.charAt(1) == '-') {
					age = Integer.parseInt(ageString.substring(0, 1));
				} else {
					age = Integer.parseInt(ageString.substring(0, 2));
				}
				float incidence = Float.parseFloat(lineSc.next()); // per 100k
				int ageRef = -1;
				HashMap<Integer, Integer> refs = new HashMap<Integer, Integer>();
				refs.put(0, 0);
				refs.put(5, 1);
				refs.put(18, 2);
				refs.put(50, 3);
				refs.put(65, 4);
				weekAgeIncidence[week-1][refs.get(age)] = incidence;
			}
		} catch (IOException io) {
			System.out.println("file i/o error");
			io.printStackTrace();
		}
		for (int i = 0; i < weekAgeIncidence.length; i++) {
			for (int j = 0; j < weekAgeIncidence[i].length; j++) {
				if(weekAgeIncidence[i][j] == 0){
					weekAgeIncidence[i][j] = 0.01f;
				}
			}
		}	
//	
		
//		for (int i = 0; i < weekAgeIncidence.length; i++) {
//			for (int j = 0; j < weekAgeIncidence[0].length; j++) {
//				float val = weekAgeIncidence[i][j];
//				System.out.println("week: " + i + ", age: " + j + ", incidence: " + val);
//			}
//		}
		
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
		
		try {
		for (int i = 0; i < weekStateLevel.length; i++) {
			Arrays.fill(weekStateLevel[i], 0.0f);
		}
		BufferedReader br2 = new BufferedReader(new FileReader(new File("data/StateData.csv")));
		Scanner sc = new Scanner(br2);
		sc.nextLine();
		HashMap<String, Integer> stateToNum = new HashMap<String, Integer>();
		ArrayList<String> badStates = new ArrayList<String>();
		badStates.add("Nevada");
		badStates.add("New York");
		int stateNum = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			Scanner lineSc = new Scanner(line).useDelimiter(",");
			String state = lineSc.next();
			if (!stateToNum.containsKey(state)) {
				stateToNum.put(state, stateNum);
				stateNum++;
			}
			lineSc.next();
			lineSc.next();
			
			if (badStates.contains(state)){
				// System.out.println("fuckin' nevada");
				lineSc.next();
			}
			String levelPlusNum = lineSc.next();
			int level = -1;
			if (levelPlusNum.substring(levelPlusNum.length() - 2).equals("10")) {
				level = 10;
			} else {
				level = Integer.parseInt(levelPlusNum.substring(levelPlusNum.length() - 1));
			}
			lineSc.next();
			lineSc.next();
			int week = Integer.parseInt(lineSc.next());
			weekStateLevel[week-1][stateToNum.get(state)] += (float) (level);
		}
		} catch (IOException io) {
			System.out.println("i/o exception");
			io.printStackTrace();
		}

		for (int i = 0; i < weekStateLevel.length; i++) {
			for (int j = 0; j < weekStateLevel[0].length; j++) {		
				weekStateLevel[i][j] /= 7.0f;
				if(weekStateLevel[i][j] == 0){
					weekStateLevel[i][j] = 0.01f;
				}	
			}
		}
		

//look at values
//		for (int i = 0; i < weekStateLevel.length; i++) {
//			for (int j = 0; j < weekStateLevel[0].length; j++) {
//				float val = weekStateLevel[i][j];
//				System.out.println("week: " + i + ", state: " + j + ", level: " + val);
//			}
//		}
		
		for(int i = 0; i < weekAgeStateScore.length; i++){
			for(int j = 0; j < weekAgeStateScore[i].length; j++){
				for(int k = 0; k < weekAgeStateScore[i][j].length; k++){
					weekAgeStateScore[i][j][k] = (float)(weekAgeIncidence[i][j] * Math.sqrt((double)(weekStateLevel[i][k])));
				}
			}
		}
		
		float[] meanDeviation = meanDeviation();
		mean = meanDeviation[0];
		std = meanDeviation[1];
	}
	
	public static float[] meanDeviation(){
		float sum = 0f;
		float dataPointCounter = 0f;
		
		for(int i = 0; i < weekAgeStateScore.length; i++){
			for(int j = 0; j < weekAgeStateScore[i].length; j++){
				for(int k = 0; k < weekAgeStateScore[i][j].length; k++){
					sum += weekAgeStateScore[i][j][k];
					dataPointCounter++;
				}
			}
		}
		
		float mean = sum / dataPointCounter;	
		float sumDiffSquared = 0f;
		
		for(int i = 0; i < weekAgeStateScore.length; i++){
			for(int j = 0; j < weekAgeStateScore[i].length; j++){
				for(int k = 0; k < weekAgeStateScore[i][j].length; k++){
					sumDiffSquared += Math.pow((weekAgeStateScore[i][j][k] - mean), 2);
					dataPointCounter++;
				}
			}
		}
		
		float std = (float)(Math.sqrt(sumDiffSquared / sum));		
		float[] result = {mean , std};
		return result;
	}
	
	public static float[] ageMeanDeviation(int age){ //age being 0-4 categories
		float sum = 0f;
		float dataPointCounter = 0f;
		
		
		for(int i = 0; i < weekAgeStateScore.length; i++){
			for(int j = 0; j < weekAgeStateScore[i][age][j]; j++){
					sum += weekAgeStateScore[i][age][j];
					dataPointCounter++;
			}
		}
		
		float mean = sum / dataPointCounter;	
		float sumDiffSquared = 0f;
		
		for(int i = 0; i < weekAgeStateScore.length; i++){
			for(int j = 0; j < weekAgeStateScore[i][age][j]; j++){
					sumDiffSquared += Math.pow((weekAgeStateScore[i][age][j] - mean), 2);
					dataPointCounter++;
				
			}
		}
		
		float std = (float)(Math.sqrt(sumDiffSquared / sum));		
		float[] result = {mean , std};
		return result;
	}
}