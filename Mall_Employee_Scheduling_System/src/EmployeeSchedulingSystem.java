

import java.util.Scanner;

/**
 * Version 0.4 palitan tong version everytime na mag edit po kayo! Always
 * refresh!!
 * 
 * We will assist as much as we can!
 */
public class EmployeeSchedulingSystem {
	/**
	 * Properties: This are the variables that will be used throughout the software
	 */
	private static Scanner scan = new Scanner(System.in);
	private static final int row = 3;
	private static final int column = 6;
	// Arrays for Days [COLUMN]
	private static String daySlotHeader[] = { "MON", "TUE", "WED", "THU", "FRI", "SAT" };
	// Arrays for Shifts [ROW]
	private static String shiftSlotHeader[] = { "M", "A", "E" };
	// Arrays for Employees [LAYERS]
	private static String employees[] = { "Kenneth", "Paz", "Diamante" };
	// Arrays for Employees Roles
	private static String employeeRoles[] = { "Homeless", "Cashier", "Security" };
	// Arrays for Mall Schedule
	private static String mallSchedule[][][] = new String[employees.length][shiftSlotHeader.length][(daySlotHeader.length + 1)];

	// user input used in Menu
	private static int input = 0;

	public static void main(String[] args) {
		System.out.println("===================================================");
		System.out.println("          MALL EMPLOYEE SCHEDULING SYSTEM          ");
		System.out.println("===================================================");
		System.out.println("Legend: M = Morning | A = Afternoon | E - Evening");

		System.out.println("\n      <<Press Enter to Begin Setup Process>>");
		scan.nextLine();

		menuController();

	}

	/*
	 * REQUIRED METHOD Assigned to : Abo-Abo ver - 1
	 */
	public static void displaySchedule() {
		
		System.out.println("===================================================");
		System.out.println("\t\t EMPLOYEE SCHEDULE");
		System.out.println("===================================================");
			
		// day headers
		System.out.print("Shift\t");
			for ( int day = 0; day < daySlotHeader.length; day++) {
				System.out.print(daySlotHeader[day] + "\t");
			}
			System.out.println();
		
			
			// Loop through the shifts (Morning, Afternoon, and Evening)
			for (int shift = 0; shift < shiftSlotHeader.length; shift++) {
				System.out.print(shiftSlotHeader[shift] + "\t");
				
				
				// Loop through the days
				for (int day = 0; day < daySlotHeader.length; day++) {
					
					int employeeCount = 0;
					
					// Loop through the employees
					for (int emp = 0; emp < employees.length; emp++) {
						if (mallSchedule[emp][shift][day] != null) {
							employeeCount++;
						}
					}
						if ( employeeCount == 0) {
							System.out.print("-\t");
					} else {
						System.out.print(employeeCount + "\t");
					}
				}
				System.out.println();
			}
			
			System.out.println("\nTOTAL EMPLOYEES PER DAY:");
			
			for (int day = 0; day < daySlotHeader.length; day++) {
				
				int total = 0; 
				
				for (int shift = 0; shift < shiftSlotHeader.length; shift++) {
					for (int emp = 0; emp < employees.length; emp++) {
						if (mallSchedule[emp][shift][day] != null) {
							total++;
						}
					}
				}
				System.out.println(daySlotHeader[day] + ": " + total);
			}
	}

	/*
	 * REQUIRED METHOD Assigned to : Paz ver - 0
	 */
	public static void assignEmployee() {
		//XXX: Waiting for Input Validation
		
		int targetSheet = 0, targetRow = 0;
		int employeeRow = 0;
		String shiftEntry = null;
		
System.out.println(">> Assigning Employee to Shift:");
		
		
		employeeSelect: while(true) { // Tags outer loop for easy breaking.
		if(input != null && input.equals("cancel")) break; //Automatically exits if user types cancel once.
		
		System.out.println("> Select Employee to Assign:");
		
		seeEmployeeList();
		System.out.println("[!] Type 'cancel' to return to menu.");
		
		input = rd.nextLine();
		
		//Loop through the employees 2D array for a match.
		for (int i = 0; i < employees.length; i++) {

			//If match is found:
			if (input.equals(employees[i][0])) {
				System.out.println("> ID match for " + input + " found.");
				shiftEntry = employees[i][1] + "(" + employees[i][2] + ")";
				employeeRow = i;
				break employeeSelect; //Break out of the Outer while loop
				
			} else if((i + 1) == employees.length)System.out.println("> No ID match for " + input + " found. Did you make a typo?");
			 // ^ Only displays on the last loop.
		}
		
		}
		
		
		
		daySelect: while(true) { // Tags outer loop for easy breaking.
		if(input != null && input.equals("cancel")) break;
		
		System.out.println("> Select Day to add Employee's shift to (MONDAY-" + daySlotHeader[days-1] + "):");
		System.out.println("[!] Type 'cancel' to return to menu.");
		
		input = rd.nextLine().toLowerCase();
		
		switch(input) {
		case "monday":
			targetSheet = 0;
			System.out.println("> Shift will be added to " + input + ".");
			break daySelect; //Breaks outer loop.
		case "tuesday":
			targetSheet = 1;
			break daySelect;
		case "wednesday":
			targetSheet = 2;
			break daySelect;
		case "thursday":
			targetSheet = 3;
		case "friday":
			targetSheet = 4;
			break daySelect;
		case "saturday":
			targetSheet = 5;
			break daySelect;
		case "cancel":			
			break daySelect;
		default:
			System.out.println("> " + input + " is not a valid day. Did you make a typo?");
			break;
		}
		}
		
		
		
		timeSelect: while(true) { // Tags outer loop for easy breaking.
			if(input != null && input.equals("cancel")) break timeSelect;
			
			System.out.println("> Select Time slot to add the shift to:");
			System.out.println("(M: Morning, A: Afternoon, E: Evening)");
			
			input = rd.nextLine().toLowerCase();
			
			switch(input) {
			case "m":
				targetRow = 0;
				break timeSelect; // Breaks outer loop.
			case "a":
				targetRow = 1;
				break timeSelect;
			case "e":
				targetRow = 2;
				break timeSelect;
			default:
				System.out.println("> " + input + " is not a valid time slot. Did you make a typo?");
				break;
			
			}
			
		}
		
		if(dupeChecker(shiftEntry)) { // Calls the method that checks if a duplicate entry exists.
			System.out.println("> Employee is already assigned to a shift for this day!");
			return; // Go back to main(), skip the assignment code.
		} 
		
		if(input != null && input.equals("cancel")) {input = null; return;}
		for(int i = 0; i < shiftSchedule[targetSheet][targetRow].length; i++) {
			
			
			if(shiftSchedule[targetSheet][targetRow][i].equals("-")) // Checks if the slot is empty.
			{
				shiftSchedule[targetSheet][targetRow][i] = shiftEntry;
				System.out.println("> " + employees[employeeRow][1] + " was assigned to " + timeSlotHeader[targetRow] + " slot for "+  daySlotHeader[targetSheet] + ".");
				break;
			} else if((i+1) == shiftSchedule[targetSheet][targetRow].length) { // Display on the last iteration.
				System.out.println("> Couldn't assign " + employees[employeeRow][1] + " to shift. " + daySlotHeader[targetSheet]+"'s " + timeSlotHeader[targetRow]+ " slot is full."); 
			}
		
		}
		
	}
	
	// XXX: Might be modifiable to double as the search feature.
		public static boolean dupeChecker(String target) {
			for(int sh = 0; sh < shiftSchedule.length; sh++) {
				for(int row = 0; row < shiftSchedule[sh].length; row++) {
					for(int col = 0; col < shiftSchedule[sh][row].length; col++) {
						
						if(shiftSchedule[sh][row][col].equals(target)) return true;
							
						}}}
				return false;
			}
			

	/*
	 * REQUIRED METHOD Assigned to : Gabriel ver - 0
	 */
	public static void validateInput() {

	}

	/*
	 * REQUIRED METHOD Assigned to : Cairo ver - 0
	 */
	public static void checkDuplicateEmployee() {

	}

	/*
	 * REQUIRED METHOD Assigned to : ? ver - 0
	 */
	public static void updateOrRemoveEmployee() {
		// Menu test delete nalang
		System.out.println("Update / Remove Employee test");
	}

	/*
	 * REQUIRED METHOD Assigned to : Galasao ver - 0
	 */
	public static void searchEmployee() {
		// Menu test delete nalang
		System.out.println("Search Employee test");
	}

	/*
	 * REQUIRED METHOD Assigned to : Granada ver - 0
	 */
	public static void calculateWorkingHours() {

	}

	/*
	 * REQUIRED METHOD Assigned to : Ken ver - 1
	 */
	public static void menuController() {
		System.out.println("===================================================");
		System.out.println("\t\t     MAIN-MENU");
		System.out.println("===================================================");
		System.out.println("[1] View Employee Schedule");
		System.out.println("[2] Assign Employee to a Shift");
		System.out.println("[3] Update / Remove Employee");
		System.out.println("[4] Search Employee");
		System.out.println("[5] Exit");

		System.out.print("\nEnter your Choice: ");
		input = scan.nextInt();

		System.out.println();

		switch (input) {
		case 1:
			displaySchedule();
			break;
		case 2:
			assignEmployee();
			break;
		case 3:
			updateOrRemoveEmployee();
			break;
		case 4:
			searchEmployee();
			break;
		case 5:
			exit();
			break;

		}
	}// menuController() method

	/*
	 * REQUIRED METHOD Assigned to : ? ver - 0
	 */
	public static void dailySummaryReport() {

	}

	public static void exit() {
		// Menu test delete nalang
		System.out.println("Thank you for using the Mall Employee Scheduling System!");
		System.out.println("Program Terminated");
	}// exit() method
}
