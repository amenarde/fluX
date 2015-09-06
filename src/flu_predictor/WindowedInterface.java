package flu_predictor;

//code framework based on http://www.dreamincode.net/forums/topic/17705-basic-gui-concepts/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

public class WindowedInterface extends JFrame
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	
	private JLabel welcome1L, welcome2L, stateL, ageL, dateL, resultL;
	private JTextField stateTF, ageTF, dateTF, resultTF;
	private JButton calculateB, exitB;
	
	//Button handlers:
	private CalculateButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	
	public static Patient user;
	
	public WindowedInterface()
	{
		welcome1L = new JLabel("<html><body>Welcome to fluX!</body></html>", SwingConstants.CENTER);
		welcome2L = new JLabel("<html><body>We can evaluate your flu risk using location, age, and seasonal data from the CDC!</body></html>");
		stateL = new JLabel("<html><body>Please enter your state of residence or New York City, District of Columbia, or Puerto Rico:</body></html>", SwingConstants.RIGHT);
		ageL = new JLabel("<html><body>Please enter your age:</body></html>", SwingConstants.RIGHT);
		dateL = new JLabel("<html><body>Date (no need to enter):</body></html>", SwingConstants.RIGHT);
		resultL = new JLabel("<html><body>Your Result:</body></html>", SwingConstants.RIGHT);
		
		stateTF = new JTextField(10);
		ageTF = new JTextField(10);
		dateTF = new JTextField(10);
		resultTF = new JTextField(10);
		
		//SPecify handlers for each button and add (register) ActionListeners to each button.
		calculateB = new JButton("Calculate");
		cbHandler = new CalculateButtonHandler();
		calculateB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		setTitle("fluX");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(6, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(welcome1L);
		pane.add(welcome2L);
		pane.add(stateL);
		pane.add(stateTF);
		pane.add(ageL);
		pane.add(ageTF);
		pane.add(dateL);
		pane.add(dateTF);
		pane.add(resultL);
		pane.add(resultTF);
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
			String state;
			int stateNum;
			int age;
			int dayNum;
			int week;
			
			state = stateTF.getText();
			stateNum = Interface.getStateNum(state);
					
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
			
			DataProcessor.getData();
			user = new Patient(age, week, stateNum);	
			float risk = CalculateRisk.risk();
			
			dateTF.setText("" + today);
			resultTF.setText("" + risk);
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		WindowedInterface mainInstance = new WindowedInterface();
	}
	
}

