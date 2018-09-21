public class FitnessClass implements Comparable<FitnessClass> {
    
	// declaring instance variables
	private String id;
	private String classname;
	private String tutorname;
	private int time;
	private int[] attendance={0,0,0,0,0};
	
	// default constructor
	public FitnessClass (String id, String classname, String tutorname, int time, int[] attendance)	{
		this.id=id;
		this.classname=classname;
		this.tutorname=tutorname;
		this.time=time;
		
		setAttendance(attendance);
	}
		
	// second constructor
	public FitnessClass (String id, String classname, String tutorname, int time)	{
		this.id=id;
		this.classname=classname;
		this.tutorname=tutorname;
		this.time=time;
	}
	
	// mutator and accessor methods for id
	public void setID (String id)	{
		this.id=id;
	}
	
	public String getID()	{
		return id;
	}
	
	// mutator and accessor methods for class name
	public void setClassName (String classname)	{
		this.classname=classname;
	}
	
	public String getClassName() {
		return classname;
	}
	
	// mutator and accessor methods for tutor's name
	public void setTutorName (String tutorname)	{
		this.tutorname=tutorname;
	}
	
	public String getTutorName ()	{
		return tutorname;
	}
	
	// mutator and accessor methods for time
	public void setTime (int time)	{
		this.time=time;
	}
	
	public int getTime ()	{
		return time;
	}
	
	// mutator and accessor methods for attendance array
	public void setAttendance (int[] attendance) {
		for (int i=0; i<5; i++) {
			this.attendance[i]=attendance[i]; 
		}
	}
	
	public int[] getAttendance () 	{
		return attendance;
	}
	
	 /**
     * method to compare FitnessClass objects
     * @param other
     * @return 1, 0 or -1
     * returns 1 if average attendance is greater
     * returns 0 if average attendance is the same
     * returns -1 if average attendance is less
     */
    public int compareTo (FitnessClass other) {
	  
    	double average=calculateAverage();
    	
    	if (average<other.calculateAverage())	{
    		return 1;
    	}
    	
    	if (average== other.calculateAverage())	{
    		return 0;
    	}
    	
    	return -1; 
    }
    
    /**
     * method to calculate the average attendance for a Fitness Class
     * @return double representing average attendance
     */
    public double calculateAverage () {
		
    	int total=0;
		
		for (int i=0; i<5; i++)	{
			total=total+attendance[i];
		}
		
		double average=(double)total/(double)5;
		return average;
	}
    
    /**
     * method to format individual Fitness Class data to appropriate form
     * String contains the ID, class, tutor, attendance and average attendance
     * to be passed into Report Frame class
     * @return String representing a Fitness Class' report data
     */
    public String reportFormat () {
    	
    	String attend="";
    	
    	for (int i=0; i<attendance.length; i++)	{
    		attend=attend+getAttendance()[i]+"  ";
    	}
    	
    	String format= "%6s%15s%15s%30s%20.2f%n";
    	String reportData= String.format(format, getID(), getClassName(), getTutorName(), attend, calculateAverage() ); 
  
    	return reportData;
    }
}

