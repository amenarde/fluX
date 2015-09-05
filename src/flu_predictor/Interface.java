package flu_predictor;

import java.time.LocalDate;
import java.util.Scanner;

public class Interface {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to fluX!");
		System.out.println("We can evaluate your flu risk using location, age, and seasonal data from the CDC!");
		System.out.println("____________________________________");
		System.out.println("Please enter your state of residence");
		System.out.println("or 'New York City', 'District of Columbia', or Puerto Rico':");	
		String state = in.next();
		
		System.out.println();
		System.out.println("____________________________________");
		System.out.println("Please enter your age:");
		int age = in.next(int);
		
		LocalDate today = LocalDate.now();
		int dayNum = today.getDayOfYear();
		int week = (int)(dayNum / 7)
		
		
	}
}
