package flu_predictor;

public class Patient {

	int age;
	int week;
	int state;
	
	public Patient (int age, int week, int state) {
		this.age = age;
		this.week = week;
		this.state = state;
	}
	
	public String toString(){
		return age + ", " + week + ", " + state;
	}
	
	public int getAge(){
		return this.age;
	}
	public int getWeek(){
		return this.week;
	}
	public int getState(){
		return this.state;
	}
}
