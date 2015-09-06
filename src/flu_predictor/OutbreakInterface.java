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

public class OutbreakInterface extends JFrame
{
	public boolean flu;
	public boolean symptom;
	public boolean vaccine;

	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	
	private JLabel welcome1L, welcome2L, fluL, symptomL, vaccineL;
	private JTextArea result1TA, result2TA;
	private JComboBox<String> fluList, symptomList, vaccineList;
	private JButton calculateB, exitB;
	
	//Button handlers:
	private CalculateButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	
	public OutbreakInterface()
	{
		getContentPane().setBackground(Color.WHITE);
		
		welcome1L = new JLabel(new ImageIcon("data/fluxlogosmall.png"));
		welcome2L = new JLabel("<html><body>fluX can use your data to help catch outbreaks at an early stage. If you would like to participate, please enter your information below.</body></html>");
		fluL = new JLabel("<html><body>Do you have or have you had a confirmed case of the flu in the last week?</body></html>", SwingConstants.RIGHT);
		symptomL = new JLabel("<html><body>If not, have you had any major symptoms of the flu in the last week? (Fever, Cough, Sore Throat, Headaches, Fatigue)</body></html>", SwingConstants.RIGHT);
		vaccineL = new JLabel("<html><body>Have you gotten a flu vaccination in the last year?</body></html>", SwingConstants.RIGHT);
		
		//statebox
		String[] blankYesNo = {"", "Yes", "No"};
				
		fluList = new JComboBox<>(blankYesNo);
		symptomList = new JComboBox<>(blankYesNo);
		vaccineList = new JComboBox<>(blankYesNo);
		
		result1TA = new JTextArea();
		result2TA = new JTextArea();
		
		//Specify handlers for each button and add (register) ActionListeners to each button.
		calculateB = new JButton("Enter");
		cbHandler = new CalculateButtonHandler();
		calculateB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		fluList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox fluList = (JComboBox) e.getSource();
				if(((String)fluList.getSelectedItem()).equals("Yes")){
					flu = true;
				}
				else if(((String)fluList.getSelectedItem()).equals("No")){
					flu = false;
				}
				else{
					flu = false;
				}
			}
		});
		symptomList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox symptomList = (JComboBox) e.getSource();
				if(((String)symptomList.getSelectedItem()).equals("Yes")){
					symptom = true;
				}
				else if(((String)symptomList.getSelectedItem()).equals("No")){
					symptom = false;
				}
				else{
					symptom = true;
				}
			}
		});
		vaccineList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox vaccineList = (JComboBox) e.getSource();
				if(((String)vaccineList.getSelectedItem()).equals("Yes")){
					vaccine = true;
				}
				else if(((String)vaccineList.getSelectedItem()).equals("No")){
					vaccine = false;
				}
				else{
					vaccine = false;
				}
			}
		});
		
		setTitle("fluX Outbreak Interface");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(6, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(welcome1L);
		pane.add(welcome2L);
		pane.add(fluL);
		pane.add(fluList);
		pane.add(symptomL);
		pane.add(symptomList);
		pane.add(vaccineL);
		pane.add(vaccineList);
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
			float[] latLong = {0, 0};
			try {
				latLong = LocationIndexer.latLong();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(latLong[0] == 0 || latLong[1] == 0){
				System.out.println("Could not calculate latLong.");
				System.exit(0);
			}
			
			float latitude  = latLong[0];
			float longitude = latLong[1];
			
			if (flu || (symptom && !vaccine) || (symptom && vaccine && CalculateRisk.risk()[0] > 1.0f)) {
				PatientData.updatePatientData(WindowedInterface.user, flu, latitude, longitude, symptom, vaccine);
			}
				
			result1TA.setLineWrap(true);
			result2TA.setLineWrap(true);
			result1TA.setWrapStyleWord(true);
			result2TA.setWrapStyleWord(true);
			result1TA.append("Your information has been added to our database.");
			result2TA.append("");
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

