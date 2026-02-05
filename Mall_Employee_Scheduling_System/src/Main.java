import java.util.Scanner;
/**
 * Version 0.2
 */

public class Main {
	static Scanner scan = new Scanner(System.in);
	// Properties
	static String shiftSchedule[][][] = new String[6][3][];
	static String daySlotHeader[] = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
	static char timeSlotHeader[] = {'M', 'A', 'E'};
	static String employees[][] = {{"DR", "Who?", "Dr. of Intestinal Parasites"},
								   {"ENGY", "Dell", "Texan"},
								   {"SLKS", "Hornet", "UsedtoBeBelievable"},
								   {"CRUS", "John Dungeon", "Crusader"}};
	static int days = 6; // faces
	static final int timeSlot = 3; //
	static int shifts; // columns
	static String input = ""; // User Input container.
	
	public static void main(String[] args) {
		Initialisation();
		while(true) {
		SeeList(0);
		Menu();
		
		}
	}
	
	public static void Menu() {
		
		System.out.println("= GENERIC SHIFT SCHEDULING MANAGER !© 2026 =");
		System.out.println("[1] View Employee Schedule\n" + 
						   "[2] Assign Employee to Shift\n" +
						   "[3] Update Employee Assignment\n" +
						   "[4] Search Employee\n" + 
						   "[5] Exit System\n");
		
		switch(Integer.parseInt(scan.nextLine())) {
		case 1: 
			
			//TODO: Employee Schedule
			
			break;
		case 2:
			
			AssignShift();
			
			break;
		case 3:
			
			//TODO: Update Employee Assignment
			
			break;
		case 4:
	
			//TODO: Search Employee
	
			break;
			case 5:
			
			System.exit(0);
			
			break;
		}	
	}
	
	public static void AssignShift() {
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
				
				if(dupeChecker(shiftEntry, targetSheet)) { // Calls the method that checks if a duplicate entry exists.
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
		public static boolean dupeChecker(String target, int sh) {
				for(int row = 0; row < shiftSchedule[sh].length; row++) {
					for(int col = 0; col < shiftSchedule[sh][row].length; col++) {
						
						if(shiftSchedule[sh][row][col].equals(target)) return true;
							
						}}
				return false;
			}
	
	
	public static void SeeList(int sheetIndex) { // sheetIndex is the 3D array's face(Programmer defined.)
			System.out.println("");
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("SHIFT SCHEDULE : " + daySlotHeader[sheetIndex]);
			
			for (int row = 0; row < shiftSchedule[sheetIndex].length; row++) {
				
				String index = shiftSchedule[sheetIndex][row][0];
				System.out.print("[" + timeSlotHeader[row] + "]");
				if (index != null) {
					for (int column = 0; column < shiftSchedule[sheetIndex][row].length; column++) {
						String item = (column != 0) ? shiftSchedule[sheetIndex][row][column - 1] : ""; //Take the last column's string length
						int spaceVariance[] = {0, 12, 18, 18};
						int spaceLength = spaceVariance[1] - item.length();
						
							
						for (int space = 1; space <= spaceLength; space++) { // e2 start
							System.out.print(" ");
						} // e1 loop end
						
						
						System.out.print(shiftSchedule[sheetIndex][row][column]);

					} // column loop end
					
				}
				System.out.println();
			}
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("");
		
		return;
	}
	
	public static void seeEmployeeList() {
		System.out.println("");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("EMPLOYEE LIST:");
		System.out.println("+--------------+--------------------+------------------+");
		System.out.println("|      ID      |        Name        |     Position     |");
		System.out.println("+--------------+--------------------+------------------+");
		for (int row = 0; row < employees.length; row++) {
			
			String index = employees[row][0];
			
			System.out.print("[" + row + "]");
			if (index != null) {
				for (int column = 0; column < employees[row].length; column++) {
					String item = (column != 0) ? employees[row][column - 1] : ""; //Take the last column's string length
					// Attempts to create equal spacing
					
					int spaceLength = 20 - item.length();
					if(column != 0) { // No spaces for column 1
					for (int space = 1; space <= spaceLength; space++) { // e2 start
						System.out.print(" ");
					} // e1 loop end
					}
					
					System.out.print(employees[row][column]);

				} // column loop end
				
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("");
	
	return;
}

	
	public static void Initialisation() {
		System.out.println("===========================================");
		System.out.println("      MALL EMPLOYEE SCHEDULING SYSTEM      ");
		System.out.println("===========================================");
		
		System.out.println("\n   <<Press Enter to Begin Setup Process>>");
		scan.nextLine();
		
		System.out.println("===========================================");
		System.out.println("Shift Legend: \n\tM = Morning   \n\tA = Afternoon \n\tE = Evening");
		System.out.println("===========================================");
		
		// Ken note: Show the Current Employee Schedule After this
		
		// Set available Shift slots per day.
		System.out.println("Set the amount of shift slots for each time slots for each day: ");
		System.out.println("M = Morning, A = Afternoon, E = Evening");
		for(int day = 0; day < days; day++) {
			System.out.println(" = >>" + daySlotHeader[day] + "<< = ");
			for(int time = 0; time < timeSlot; time++) {
				System.out.print("Set Shift Slots for " + timeSlotHeader[time]  + " of "+ daySlotHeader[day] + ": ");
				while(true) {
					try {
					shiftSchedule[day][time] = new String[Integer.parseInt(scan.nextLine())];	
					break;
					} catch(NumberFormatException e) {
						System.out.println("> Error: Invalid Input! ");
					}
				}
			}
		}	
				// Fill all slots with placeholder text.
				for (int s = 0; s < shiftSchedule.length; s++) {
					for (int r = 0; r < shiftSchedule[s].length; r++) {
						for (int c = 0; c < shiftSchedule[s][r].length; c++) {
					
							shiftSchedule[s][r][c] = "-";
					}
				}
			}
	}
}
