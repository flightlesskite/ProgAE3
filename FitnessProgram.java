import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
 
public class FitnessProgram {
    // instance variables
	
	final int maxCapacity=7;
	private int currentCapacity;
	private FitnessClass[] fList;
	
	// default constructor (with no FitnessClass objects)
	public FitnessProgram ()	{
		
		currentCapacity=0;
		fList= new FitnessClass[maxCapacity];
	}
	
	/**
	 * @param Fitness Class object fc
	 * finds earliest time to create index order of array
	 * (time-9) is index 0 etc.
	 * initialises list in order of start time
	 */
	public void setClass (FitnessClass fc) {
		
		int i=(fc.getTime()-9);
		fList[i]=fc;
		currentCapacity++;
	}
	
	// accessor method for returning FitnessClass array
	public FitnessClass[] getList () {
		return fList;
	}
	
	// accessor method for returning int representing number of classes in array
	public int getCapacity () {
		return currentCapacity;
	}
	
	/**
	 * @param t- int representing starting time
	 * @return Fitness Class object with start time t
	 * checks range for time beginning at 9 to 15 (3pm)
	 * Returns null if no corresponding class at time t
	 */
	public FitnessClass classAtTime (int t) {
		if (t>=9 && t<=15)	{
			return fList[t-9];
		}	
		return null;
	}
	
	/**
	 * @param id- String representing unique class ID
	 * @return Fitness Class object with unique class ID
	 * Returns null if no class present with t
	 */
	public FitnessClass classByID (String id) {
		
		for (int i=0; i<maxCapacity; i++)	{
			if (fList[i]!=null && fList[i].getID().equals(id))	{
				return fList[i];
			}
		}
		return null;
	}
	
	/**
	 * @return array sorted (in descending order)
	 * sorts list of all Fitness Classes (fList)
	 * stores new list into a FitnessClass array
	 */
	public FitnessClass[] sortList() {
		
		FitnessClass sortClass[]=new FitnessClass[currentCapacity];
		int counter=0;
		
		for (int i=0; i<maxCapacity; i++)	{
			if(fList[i] !=null)	{
				
				sortClass[counter]=fList[i];
				counter++;
			}
		}
		Arrays.sort(sortClass);
		return sortClass;
	}
	
	/**
	 * @return double representing an overall average attendance of all classes in list
	 * calculates total of individual Fitness Class numbers
	 * divide total by current number of classes in list
	 */
	public double overallAverage ()	{
		
		double total=0;
		
		for (int i=0; i<maxCapacity; i++)	{
			if (fList[i]!=null)
				total=total+fList[i].calculateAverage();
		}
		
		double average=Math.round((total/currentCapacity)*100.0)/100.0;
		// Math.round used to round correctly
		return average;
	}
	
	/**
	 * @return int representing an available time slot 
	 * for adding a new Fitness Class
	 * identifies empty/null index and adds 9 for time value
	 */
	public int availableTime() {
		for (int i=0; i<maxCapacity; i++) {
			if (fList[i]== null)	{
				return i+9;
			}
		}	
		return 0;
	}
	
	/**
	 * @param Fitness Class
	 * @return true or false depending on whether adding was successful
	 * checks for an unoccupied array index
	 * adds the new Fitness Class object
	 * current capacity of list increments by 1
	 */
	public boolean addClass(FitnessClass fc) {
		
		for (int i=0; i<maxCapacity; i++) {
			if (fList[i]== null) {
				fList[i]=fc;
				currentCapacity++;
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * @param id
	 * @return true or false depending on test for duplicate IDs
	 * returns true if id input already exists
	 * returns false if id input is available
	 */
	public boolean duplicateID (String id) {
		
		for (int i=0; i<maxCapacity; i++) {
			if (fList[i]!=null && fList[i].getID().equals(id))	{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param id
	 * @return true or false depending on whether deletion was successful
	 * uses id input to locate a Fitness Class with corresponding id
	 * removes this Fitness Class from list
	 * current capacity of list decreases by 1
	 */
	public boolean deleteClass (String id)	{
		
		for(int i=0; i<maxCapacity; i++)	{
			
			if(fList[i]!=null && fList[i].getID().equals(id)) {
				fList[i]=null;
				currentCapacity--;
				return true;
			}
		}
		return false;
	}
	
}
