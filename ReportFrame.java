import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
    
	/** display of class attendances */
	private JTextArea report;
	
	/** constructor for Report Frame */
	public ReportFrame ()	{
		
		setTitle ("Attendance Report");
		setSize (700,300);
		report= new JTextArea();
		report.setFont(new Font("monospaced", Font.PLAIN, 12));
		add (report, BorderLayout.CENTER);
	}
	
	/**
	 * @param fc
	 * @param overallAvg
	 * write attendance report using Fitness Class and its method: reportFormat
	 * report details each class' ID, name, tutor, attendance & the overall attendance for all classes
	 */
	public void buildReport (FitnessClass[] fc, double overallAvg) {
		
		String title= String.format("%6s%15s%15s%28s%30s%n", "ID", "Class", "Tutor", "Attendance", "Average Attendance");
		String border= ("   ==========================================================================================="+"\n");
		report.setText(title);
		report.append(border);
		
		for (int i=0; i<fc.length; i++) {
			if (fc[i]!=null)	{
				report.append(fc[i].reportFormat());
			}	
		}
		
		report.append(String.format("%n%70s%1.2f", "Overall Average: ", overallAvg));
		
	}
}
