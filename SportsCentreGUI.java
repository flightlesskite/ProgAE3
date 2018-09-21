import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	FitnessProgram fp=new FitnessProgram();
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		// initialises Fitness Program List
		initLadiesDay();
		// displays timetable
		updateDisplay();
		
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
	    		
		try {
	    	FileReader reader= new FileReader(classesInFile);
	    	String line="";
	    	Scanner in=new Scanner(reader);
	    	
	    	// reading from file line by line
	    	while (in.hasNextLine())	{
	    		line=in.nextLine();
	    		// splitting line read in separate words
	    		String tokens[]=line.split(" ");
	 
	    			// creates a new Fitness Class object 
	    			// using id, class name, instructor name and time read from file
	    			String id= tokens [0];
	    			String classname= tokens [1];
	    			String instructorname= tokens [2];
	    			int time= Integer.parseInt(tokens [3]);
	    			FitnessClass fc=new FitnessClass (id, classname, instructorname, time);
	    			
	    			// adds new Fitness Class to Fitness Program array
	    			fp.setClass(fc);   			
	    		} 
	    	reader.close();
	    	in.close();
	    	initAttendances();
	    }	
	    catch (IOException e)	{
	    	System.err.println("Reading from file error.");
	    }
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
		
		try {
	    	FileReader reader= new FileReader(attendancesFile);
	    	String line="";
	    	Scanner in=new Scanner(reader);
	    	
	    // reading from file line by line
	    	while (in.hasNextLine())	{
	    		line=in.nextLine();
	    		// splitting line read in separate words
	    		String tokens[]=line.split("\\s+");
	    		String id= "";
	    		
	    		// sets attendances for each Fitness Class
	    		int attendance []= {0, 0, 0, 0, 0};
	    		int j=0;
	    		for (int i=0; i<5; i++)	{
	    			// increment index j by 1 to skip over ID (index 0)
	    			j++;
	    			attendance[i]= Integer.parseInt(tokens[j]);
	    			id= tokens[0];  	
	    		}
	    		// uses Fitness Program method to find Fitness Class with given ID
    			// sets the attendance for Fitness Class with given ID
	    		fp.classByID(id).setAttendance(attendance);    		
	    	}    
	    	reader.close();
	    	in.close();
	    }	
	    catch (IOException e)	{
	    	System.err.println("Reading from file error.");
	    }
	}
    		

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
		
		// creates timetable header
		// times from 9-10am to 3-4pm
		String title= String.format("%9s%12s%12s%12s%12s%12s%12s%n%n", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");
		display.setText(title);
		String className="";
		String tutorName="";
		String format="%12s";
		
		// checks for Fitness Class with start time 9am
		// displays class starting at 9am with the allocated tutor
		// displays Available if no Fitness Class at 9am
		if (fp.classAtTime(9)==null)	{
			className=String.format("%9s", "Available");
			tutorName= tutorName+String.format("%9s", "");
		}
		else		{
		className=String.format("%9s", fp.classAtTime(9).getClassName());
		tutorName=String.format("%9s", fp.classAtTime(9).getTutorName());
		}

		
		// checks for Fitness Class with start time i- from 10am to 3pm (15:00)
		// displays class starting at time i 
		// displays Available if no Fitness Class at time i
		for (int i=10; i<16; i++)	{
			if (fp.classAtTime(i)==null)	
				className= className+String.format(format, "Available");
			else
				className= className+String.format(format, fp.classAtTime(i).getClassName());
		}
		className=className+"\n"+"\n";
		// adds row to JTextArea
		display.append(className);
		
		// checks for Fitness Class with start time i- from 10am to 3pm (15:00)
		// displays tutor taking class starting at time i 
		for (int j=10; j<16; j++)	{
			if (fp.classAtTime(j)== null)
				tutorName= tutorName+String.format(format, "");
			else
				tutorName= tutorName+String.format(format, fp.classAtTime(j).getTutorName());
		}
		// adds row to JTextArea
		display.append(tutorName);
		
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 * displays error message if ID, class or tutor input fields are empty
	 * displays error message when there are no more available time slots (max 7)
	 * displays error message if a duplicate ID is used for a new class
	 * confirmation message appears if a class is successfully added
	 */
	public void processAdding() {
	    
		
		if (idIn.getText().isEmpty())	
			JOptionPane.showMessageDialog(null, "Please enter an ID.", "Warning", JOptionPane.ERROR_MESSAGE);
		else if (classIn.getText().isEmpty())	
			JOptionPane.showMessageDialog(null, "Please enter a class.", "Warning", JOptionPane.ERROR_MESSAGE);
		else if (tutorIn.getText().isEmpty())	
			JOptionPane.showMessageDialog(null, "Please enter a tutor.", "Warning", JOptionPane.ERROR_MESSAGE);
		else if (fp.availableTime()==0)	{
			JOptionPane.showMessageDialog(null, "There are no available time slots.", "Warning", JOptionPane.ERROR_MESSAGE);
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}	
		else if (fp.duplicateID(idIn.getText())==true)	{
			JOptionPane.showMessageDialog(null, "Duplicate ID. Please enter another one", "Warning", JOptionPane.ERROR_MESSAGE);
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}	
		else {
			FitnessClass newFC= new FitnessClass(idIn.getText(), classIn.getText(), tutorIn.getText(), fp.availableTime());
			if (fp.addClass(newFC)==true){
			JOptionPane.showMessageDialog(null, (idIn.getText() +" was successfully added."), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			updateDisplay();
			}
		}
	}

	/**
	 * Processes deleting a class
	 * matches input to the unique ID of a class 
	 * message displays to notify whether the deletion was successful 
	 * or unsuccessful if the ID input does not exist
	 * 
	 */
	public void processDeletion() {
	    
		if (fp.deleteClass(idIn.getText())==true){
			JOptionPane.showMessageDialog(null, (idIn.getText() +" was successfully deleted."), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}
		else	{
			JOptionPane.showMessageDialog(null, "No such id exists. Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}	
	    updateDisplay();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 * creates a new ReportFrame object
	 */
	public void displayReport() {
	    
		report= new ReportFrame();
	    
	    // passes list of Fitness Programs (sorted by descending order of average attendance) 
	    // and overall average as a parameter for building a report
	    report.buildReport(fp.sortList(), fp.overallAverage());
	    report.setVisible(true);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {

		String output="";
		
		// checks for Fitness Class with start time i- from 9am to 3pm (15:00)
		// uses Fitness Class methods to retrieve details 
		for (int i=9; i<16; i++)	{
			FitnessClass fc= fp.classAtTime(i);
			if (fc!=null)	{
				output=output+fc.getID()+" "+fc.getClassName()+" "+fc.getTutorName() +" "+fc.getTime()+"\r\n";
			}
		}
		
		// writes each line of Fitness Class details to output file
		try {
			FileWriter fw= new FileWriter (classesOutFile);
			fw.write(output);
			fw.close();
			JOptionPane.showMessageDialog(null, ("Report is saved."), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);	
		}
		catch (IOException e)	{
			JOptionPane.showMessageDialog(null, "Error in saving report.", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    
		// view attendances button opens a new window with attendance report
		if (ae.getSource().equals(attendanceButton)) {
			displayReport();
		}
		// add button creates a new Fitness Class using inputs from user
		else if (ae.getSource().equals(addButton))	{
			processAdding();
		}
		// delete button removes a Fitness Class using the ID input from user
		else if (ae.getSource().equals(deleteButton))	{
			processDeletion();
		}
		// save and exit button closes the program and produces an output file of classes
		else if (ae.getSource().equals(closeButton))	{
			processSaveAndClose();
		}
	}
}
