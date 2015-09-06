package flu_predictor;

//code framework based on http://www.dreamincode.net/forums/topic/17705-basic-gui-concepts/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class WindowedInterface extends JFrame
{
	public boolean outbreakInterfaceIsOpen;
	
	public static HashMap<String, Integer> stateNumHash = new HashMap<String, Integer>();
	public static HashMap<String, Integer> stateAbbrev = new HashMap<String, Integer>();
	
	public static String state = "";
	public static int stateNum = 0;
	
	public static int getStateNum() {
		state = state.toLowerCase();
		if (stateNumHash.size() == 0) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File("data/states.csv")));
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(br);
				int num = 0;
				while (sc.hasNextLine()) {
					String line = sc.nextLine().toLowerCase();
					@SuppressWarnings("resource")
					Scanner lineSc = new Scanner(line).useDelimiter(",");
					String s = lineSc.next();
					String abbrev = lineSc.next();
					s = s.substring(1, s.length() - 1);
					abbrev = abbrev.substring(1, abbrev.length() - 1);
					stateNumHash.put(s, num);
					stateAbbrev.put(abbrev, num);
					num++;
				}
			} catch (IOException io) {
				System.out.println("file i/o error in states.csv file");
			}
		}
		if (stateNumHash.containsKey(state)) {
			return stateNumHash.get(state);
		} else if (stateAbbrev.containsKey(state)){
			return stateAbbrev.get(state);
		} else {
			System.out.println("input did not match a state... program aborted");
			System.exit(1);
			return -1;
		}
	}
	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	
	private JLabel welcome1L, welcome2L, stateL, ageL, dateL;
	private JTextField ageTF, dateTF;
	private JTextArea result1TA, result2TA;
	private JComboBox<String> stateList;
	private JButton calculateB, exitB;
	
	//Button handlers:
	private CalculateButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	
	public static Patient user;
	
	public WindowedInterface()
	{
		outbreakInterfaceIsOpen = false;
		getContentPane().setBackground(Color.WHITE);
		
		welcome1L = new JLabel(new ImageIcon("data/fluxlogosmall.png"));
		welcome2L = new JLabel("<html><body>fluX can evaluate your flu risk using location, age, and seasonal data from the CDC!</body></html>");
		stateL = new JLabel("<html><body>Please enter your state of residence or New York City, District of Columbia, or Puerto Rico:</body></html>", SwingConstants.RIGHT);
		ageL = new JLabel("<html><body>Please enter your age:</body></html>", SwingConstants.RIGHT);
		dateL = new JLabel("<html><body>Date (no need to enter):</body></html>", SwingConstants.RIGHT);
		
		//statebox
		String[] states = {"", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
				"Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
				"Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
				"Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
				"Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas",
				"Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "New York City",
				"Puerto Rico", "District of Columbia"};
				
		stateList = new JComboBox<>(states);
		
		ageTF = new JTextField(10);
		dateTF = new JTextField(10);
		result1TA = new JTextArea();
		result2TA = new JTextArea();
		
		//Specify handlers for each button and add (register) ActionListeners to each button.
		calculateB = new JButton("Calculate");
		cbHandler = new CalculateButtonHandler();
		calculateB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		stateList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox statesList = (JComboBox) e.getSource();
				state = (String) statesList.getSelectedItem();
			}
		});
		
		setTitle("fluX Flu Risk Evaluator");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(6, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(welcome1L);
		pane.add(welcome2L);
		pane.add(stateL);
		pane.add(stateList);
		pane.add(ageL);
		pane.add(ageTF);
		pane.add(dateL);
		pane.add(dateTF);
		pane.add(result1TA);
		pane.add(result2TA);
		pane.add(calculateB);
		pane.add(exitB);
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class CalculateButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int age;
			int dayNum;
			int week;
			
			if(state.equals("")){
				dateTF.setText("Please choose a state.");
			}
			else{
				stateNum = getStateNum();
					
				age = Integer.parseInt(ageTF.getText());
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
				dayNum = today.getDayOfYear();
				week = (int)(dayNum / 7);
//main				
				user = new Patient(age, week, stateNum);	
				DataProcessor.getData();
				float[] risk = CalculateRisk.risk();
				String[] results = EvaluateRisk.advice(risk);
//				
				dateTF.setText("" + today);
				result1TA.setLineWrap(true);
				result2TA.setLineWrap(true);
				result1TA.setWrapStyleWord(true);
				result2TA.setWrapStyleWord(true);
				result1TA.append(results[0]);
				result2TA.append(results[1]);
				
				if(!outbreakInterfaceIsOpen){
					new OutbreakInterface();
					outbreakInterfaceIsOpen = true;
				}
			}
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
}	
