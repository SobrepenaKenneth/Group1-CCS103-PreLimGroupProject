
import java.util.Scanner;

/**
 * Version 1.0 palitan tong version everytime na mag edit po kayo! Always
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
	private static String daySlotHeader[] = 
    { "MONDAY       ", 
      "TUESDAY      ", 
      "WEDNESDAY    ", 
      "THURSDAY     ",
      "FRIDAY       ", 
      "SATURDAY     " };
	// Arrays for Shifts [ROW]
	private static String shiftSlotHeader[] = { "M", "A", "E" };
	// Arrays for Employees [LAYERS]
	private static String employees[][] = { { "DR", "Who?", "Dr. of Intestinal Parasites" },
			{ "ENGY", "Dell", "Texan" }, { "SLKS", "Hornet", "UsedtoBeBelievable" },
			{ "CRUS", "John Dungeon", "Crusader" } };
	// Arrays for Employees Roles
	private static String employeeRoles[] = { "Homeless", "Cashier", "Security" };
	// Arrays for Mall Schedule
	private static String mallSchedule[][][] = new String[employees.length][shiftSlotHeader.length][(daySlotHeader.length
			+ 1)];
	private static int days = 6; // faces
	static final int timeSlot = 3; //
	static int shifts; // columns
	static String userInput = ""; // User Input container.

	// user input used in Menu
	private static int input = 0;

	public static void main(String[] args) {
		System.out.println("====================================================================");
		System.out.println("                   MALL EMPLOYEE SCHEDULING SYSTEM                  ");
		System.out.println("====================================================================");
		System.out.println("Legend: M = Morning | A = Afternoon | E - Evening");

		System.out.println("\n\t        <<Press Enter to Begin Setup Process>>");
		scan.nextLine();
		displaySchedule();
		menuController();

	}

	/*
	 * REQUIRED METHOD Assigned to : Abo-Abo ver - 1
	 */
	public static void displaySchedule() {

		System.out.println("====================================================================");
		System.out.println("\t\t         EMPLOYEE SCHEDULE");
		System.out.println("====================================================================");
		System.out.println("DAY          MORNING(M)           AFTERNOON(A)            EVENING(E)");

		// Loop through each DAY
		for (int day = 0; day < daySlotHeader.length; day++) {

			int maxRows = 1;

			// This find MAX STACKED rows
			for (int shift = 0; shift < shiftSlotHeader.length; shift++) {

				int count = 0;
				// Loop through each EMPLOYEE
				for (int emp = 0; emp < employees.length; emp++) {
					if (mallSchedule[emp][shift][day] == null)
						continue;
					count++;
				}

				if (count > maxRows) {
					maxRows = count;
				}
			}

			// This print STACKED ROWs
			for (int row = 0; row < maxRows; row++) {

				// Print day only on first row
				if (row == 0) {
					System.out.print(daySlotHeader[day]);
				} else {
					System.out.print("*************"); // 16 spaces

				}

				// Loop through SHIFTs
				for (int shift = 0; shift < shiftSlotHeader.length; shift++) {
					int index = 0;
					String output = "   ";
					// This find EMPLOYEE for Row
					for (int emp = 0; emp < employees.length; emp++) {
						if (mallSchedule[emp][shift][day] == null)
							continue;
						if (index == row) {
							output = mallSchedule[emp][shift][day]; // print the actual assigned employee
							break;
						}
						index++;
					}
					System.out.print(output + "**********"); // 6 spaces

				}

				System.out.println();
			}
		}

		// TOTAL EMPLOYEES PER DAY
		System.out.println("\nTOTAL EMPLOYEES PER DAY:");

		for (int day = 0; day < daySlotHeader.length; day++) {

			int total = 0;

			for (int shift = 0; shift < shiftSlotHeader.length; shift++) {
				for (int emp = 0; emp < employees.length; emp++) {

					if (mallSchedule[emp][shift][day] == null)
						continue;
					total++;
				}
			}

			System.out.println(daySlotHeader[day] + ": " + total);
		}
		System.out.println();
		menuController();
	}

	/*
	 * REQUIRED METHOD Assigned to : Paz ver - 0
	 */
	public static void assignEmployee() {
		scan.nextLine(); // consume leftover newline
		System.out.println("====================================================================");
		System.out.println("\t\t   ASSIGN EMPLOYEE");
		System.out.println("====================================================================");

		// --- Step 1: Input Day ---
		System.out.print("Enter Day (MON, TUE, WED, THU, FRI, SAT): ");
		String dayInput = scan.nextLine().toUpperCase();

		int dayIndex = -1;

		switch (dayInput) {
		case "MON":
			dayIndex = 0;
			break;
		case "TUE":
			dayIndex = 1;
			break;
		case "WED":
			dayIndex = 2;
			break;
		case "THU":
			dayIndex = 3;
			break;
		case "FRI":
			dayIndex = 4;
			break;
		case "SAT":
			dayIndex = 5;
			break;
		default:
			dayIndex = -1;
		}

		if (dayIndex == -1) {
			System.out.println("Invalid Day!");
			return;
		}

		// --- Step 2: Input Shift ---
		System.out.print("Enter Shift (M/A/E): ");
		String shiftInput = scan.nextLine().toUpperCase();

		int shiftIndex = -1;

		switch (shiftInput) {
		case "M":
			shiftIndex = 0;
			break;
		case "A":
			shiftIndex = 1;
			break;
		case "E":
			shiftIndex = 2;
			break;
		default:
			shiftIndex = -1;
		}

		if (shiftIndex == -1) {
			System.out.println("Invalid Shift!");
			return;
		}

		// --- Step 3: Input Employee Info ---
		System.out.print("Enter Employee ID: ");
		String empID = scan.nextLine();

		System.out.print("Enter Employee Name: ");
		String empName = scan.nextLine();

		System.out.print("Enter Role: ");
		String empRole = scan.nextLine();

		// --- Step 4: Check for duplicate on the same day ---
		boolean duplicate = false;

		for (int emp = 0; emp < mallSchedule.length; emp++) {
			for (int s = 0; s < shiftSlotHeader.length; s++) {
				String assigned = mallSchedule[emp][s][dayIndex];
				if (assigned != null && assigned.startsWith(empID + " -")) {
					duplicate = true;
					break;
				}
			}
			if (duplicate)
				break;
		}

		if (duplicate) {
			System.out.println("SCHEDULE ALREADY ASSIGNED FOR THIS EMPLOYEE!");
			return;
		}

		// --- Step 5: Assign employee to the first available slot in the shift ---
		boolean assigned = false;
		for (int emp = 0; emp < mallSchedule.length; emp++) {
			if (mallSchedule[emp][shiftIndex][dayIndex] == null) {
				mallSchedule[emp][shiftIndex][dayIndex] = empID + " - " + empName;
				assigned = true;
				break;
			}
		}

		if (assigned) {
			System.out.println("\nEmployee successfully assigned.\n");
		} else {
			System.out.println("\nShift is already full. Cannot assign employee.\n");
			return;
		}

		// --- Step 6: Display updated schedule ---
		displaySchedule();
	}

	/*
	 * REQUIRED METHOD Assigned to : Ken ver - 1
	 */
	public static void menuController() {
		System.out.println("====================================================================");
		System.out.println("\t\t\t     MAIN-MENU");
		System.out.println("====================================================================");
		System.out.println("[1] View Employee Schedule");
		System.out.println("[2] Assign Employee to a Shift");
		System.out.println("[3] Update / Remove Employee");
		System.out.println("[4] Search Employee");
		System.out.println("[5] Daily Summary Report");
		System.out.println("[6] Exit");

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
			break;
		case 4:
			searchEmployee();
			break;
		case 5:
			exit();
			break;
		case 6:
			exit();
			break;
		default:
			System.out.println("");
			menuController();
		}
	}// menuController() method

	public static void searchEmployee() {
		System.out.println("====================================================================");
		System.out.println("\t\t SEARCH EMPLOYEE");
		System.out.println("====================================================================");

		scan.nextLine();

		System.out.print("Enter Employee Name to Search: ");
		String searchName = scan.nextLine();

		boolean found = false;
		int employeeIndex = 0;

		// Find the employee index based on name that user input
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].equals(searchName)) {
				found = true;
				employeeIndex = i;
				break;
			}
		}

		if (found) {
			System.out.println("\n--- Employee Found! ---");
			System.out.println("Name: " + employees[employeeIndex]);
			System.out.println("Role: " + employeeRoles[employeeIndex]);
			System.out.println("-----------------------");
			System.out.println("Current Schedule:");

			boolean hasSchedule = false;

			// Look for each index to find the employeeSchedule
			// mallSchedule dimensions: [Employee][Shift][Day]
			for (int d = 0; d < daySlotHeader.length; d++) {
				for (int s = 0; s < shiftSlotHeader.length; s++) {

					// Check if the slot have variable and not null
					String slotValue = mallSchedule[employeeIndex][s][d];

					if (slotValue != null && !slotValue.isEmpty()) {
						System.out.println("> " + daySlotHeader[d] + " - " + shiftSlotHeader[s] + " Shift");
						hasSchedule = true;
					}
				}
			}

			if (!hasSchedule) {
				System.out.println("No shifts assigned yet.");
			}

		} else {
			System.out.println("\n Invalid: Employee \" " + searchName + "\" not found in the database.");
		}

		// Short pause before going back to menu
		System.out.println("\nPress Enter to return to Main Menu...");
		scan.nextLine();
		menuController();
	}

	public static void exit() {
		// Menu test delete nalang
		System.out.println("Thank you for using the Mall Employee Scheduling System!");
		System.out.println("Program Terminated");
	}// exit() method
}
