package flu_predictor;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Interface {
	
	public static HashMap<String, Integer> stateNum = new HashMap<String, Integer>();
	public static Patient user;
	
	public static int getStateNum(String state) {
		if (stateNum.size() == 0) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File("data/states.csv")));
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(br);
				int num = 0;
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					@SuppressWarnings("resource")
					Scanner lineSc = new Scanner(line).useDelimiter(",");
					String s = lineSc.next();
					stateNum.put(s.substring(1, s.length() - 1), num);
					num++;
				}
			} catch (IOException io) {
				System.out.println("You screwed something up with the stateNum file");
			}
		}
		return stateNum.get(state);
	}
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to fluX!");
		System.out.println("We can evaluate your flu risk using location, age, and seasonal data from the CDC!");
		System.out.println("____________________________________");
		System.out.println("Please enter your state of residence");
		System.out.println("or 'New York City', 'District of Columbia', or Puerto Rico':");	
		String state = in.nextLine();
		int stateInteger = getStateNum(state);
		
		System.out.println();
		System.out.println("____________________________________");
		System.out.println("Please enter your age:");
		int age = in.nextInt();
		System.out.println();
		
		if(age < 5){
			age = 0;
		}
		else if(age < 18){
			age = 1;
		}
		else if(age < 50){
			age = 2;
		}
		else if(age < 65){
			age = 3;
		}
		else{
			age = 4;
		}
		
		LocalDate today = LocalDate.now();
		int dayNum = today.getDayOfYear();
		int week = (int)(dayNum / 7);
		
		week = 3;
		
		DataProcessor.getData();
		user = new Patient(age, week, stateInteger);	
		float risk = CalculateRisk.risk();
		System.out.println(risk);
	}
}
