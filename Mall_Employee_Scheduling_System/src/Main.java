import java.util.*;

/**
 * Version 1.4 palitan tong version everytime na mag edit po kayo! Always
 * refresh!!
 * 
 * We will assist as much as we can!
 */
public class Main {
	/**
	 * Properties: This are the variables that will be used throughout the software
	 */
	private static Scanner scan = new Scanner(System.in);
	private static final int row = 3;
	private static final int column = 6;
	// Arrays for Days [COLUMN]
	private static String daySlotHeader[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
	// Arrays for Shifts [ROW]
	private static String shiftSlotHeader[] = { "M", "A", "E" };
	// XXX: REDUNDANT
//	// Arrays for Employees [LAYERS]
//	private static String employees[][] = { { "DR", "Who?", "Dr. of Intestinal Parasites" },
//			{ "ENGY", "Dell", "Texan" }, { "SLKS", "Hornet", "UsedtoBeBelievable" },
//			{ "CRUS", "John Dungeon", "Crusader" } };
//	// Arrays for Employees Roles
//	private static String employeeRoles[] = { "Homeless", "Cashier", "Security" };
	// Arrays for Mall Schedule
	private static String mallSchedule[][][] = new String[6][3][10]; // DAYS - SHIFTS - Employee
	private static int days = 6; // faces
	static final int timeSlot = 3; //
	static int shifts; // columns
	static String userInput = ""; // User Input container.

	// user input used in Menu
	private static int input = 0;

	public static void main(String[] args) {
		System.out.println("======================================================");
		System.out.println("            MALL EMPLOYEE SCHEDULING SYSTEM          ");
		System.out.println("======================================================");
		System.out.println("Legend: M = Morning | A = Afternoon | E - Evening");

		System.out.println("\n      <<Press Enter to Begin Setup Process>>");
		scan.nextLine();

		while (true) {
			displaySchedule();
			menuController();
		}

	}

	/*
	 * REQUIRED METHOD Assigned to : Abo-Abo ver - 1
	 */
	public static void displaySchedule() {
		int spaceLength = 0;
		System.out.println("======================================================");
		System.out.println("\t\t\t   EMPLOYEE SCHEDULE");
		System.out.println("======================================================");
		System.out.println("DAY            MORNING(M)                    AFTERNOON(A)                  EVENING(E)");

		// Loop through each DAY
		for (int day = 0; day < mallSchedule.length; day++) {

			int maxRows = 1;

			// This find MAX STACKED rows
			for (int shift = 0; shift < mallSchedule[day].length; shift++) {

				int count = 0;
				// Loop through each EMPLOYEE
				for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {
					if (mallSchedule[day][shift][emp] == null)
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
					for (int space = 1; space <= 15 - daySlotHeader[day].length(); space++) { // e2 start
						System.out.print(" ");
					}

				} else {
					System.out.print("            "); // 12 spaces

				}

				// Loop through SHIFTs
				for (int shift = 0; shift < mallSchedule[0].length; shift++) {

					int index = 0;
					String output = " ";
					// This find EMPLOYEE for Row

					for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {

						if (mallSchedule[day][shift][emp] == null) {
							output = "-";
							spaceLength = 30 - output.length();

							continue;
						}
						if (index == row) {
							output = mallSchedule[day][shift][emp]; // print the actual assigned employee
							spaceLength = 30 - output.length();
							break;
						}
						index++;
					}

					// No spaces for column 1
					System.out.print(output);
					for (int space = 1; space <= spaceLength; space++) { // e2 start
						System.out.print(" ");
					} // e1 loop end

				}

				System.out.println();
			}
		}

		// TOTAL EMPLOYEES PER DAY
		System.out.println("\nTOTAL EMPLOYEES PER DAY:");

		for (int day = 0; day < mallSchedule.length; day++) {

			int total = 0;

			for (int shift = 0; shift < mallSchedule[day].length; shift++) {
				for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {

					if (mallSchedule[day][shift][emp] == null)
						continue;
					total++;
				}
			}

			System.out.println(daySlotHeader[day] + ": " + total);
		}
		System.out.println();
	}

	/*
	 * REQUIRED METHOD Assigned to : Paz ver - 0
	 */
	public static void assignEmployee() {

		int shiftIndex, dayIndex;
		boolean assigned = false;

		System.out.println("======================================================");
		System.out.println("\t\t   ASSIGN EMPLOYEE");
		System.out.println("======================================================");
		// --- Step 1: Input Day ---

		dayIndex = dayValidation();

		// --- Step 2: Input Shift ---

		shiftIndex = shiftValidation();

		// --- Step 3: Input Employee Info ---
		System.out.print("Enter Employee ID: ");
		String empID = scan.nextLine();

		System.out.print("Enter Employee Name: ");
		String empName = scan.nextLine();

		System.out.print("Enter Role: ");
		String empRole = scan.nextLine();

		String entry = empID + " - " + empName + " (" + empRole + ")";

		// --- Step 4: Check for duplicate on the same day ---
		// Calls the duplicate checking method which returns the earliest matching
		// entry.
		String dupeValue = dupeChecker(empID + " -", dayIndex);
		System.out.println("> " + dupeValue); // debug, delete
		if (dupeValue == null) {

			for (int i = 0; i < mallSchedule[dayIndex][shiftIndex].length; i++) {

				if (mallSchedule[dayIndex][shiftIndex][i] == null || mallSchedule[dayIndex][shiftIndex][i].equals("-")) // Checks
																														// if
																														// the
																														// slot
																														// is
																														// empty.
				{
					mallSchedule[dayIndex][shiftIndex][i] = entry;
					System.out.println("> " + empName + " was assigned to " + shiftSlotHeader[shiftIndex] + " slot for "
							+ daySlotHeader[dayIndex] + ".");
					assigned = true;
					break;
				}
			}
			if (!assigned)
				System.out.println("> Couldn't assign " + empName + ", all " + shiftSlotHeader[shiftIndex]
						+ " slots for " + daySlotHeader[dayIndex] + " are filled.");

		} else
			System.out.println("> " + empName + " is already assigned on a shift for " + shiftSlotHeader[shiftIndex]
					+ " of " + daySlotHeader[dayIndex] + "!");

		// --- Step 6: Display updated schedule ---

		// displaySchedule();
	}

	private static int dayValidation() {

		int dayIndex = -1;
		while (dayIndex == -1) {
			System.out.print("Enter Day (MON, TUE, WED, THU, FRI, SAT): ");
			String dayInput = scan.nextLine().toUpperCase();

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
			}

		}
		return dayIndex;

	}

	private static int shiftValidation() {

		int shiftIndex = -1;
		while (shiftIndex == -1) {
			System.out.print("Enter Shift (M/A/E): ");
			String shiftInput = scan.nextLine().toUpperCase();

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
			}
		}
		return shiftIndex;

	}

	/*
	 * REQUIRED METHOD Assigned to : Ken ver - 1
	 */
	public static void menuController() {
		System.out.println("======================================================");
		System.out.println("\t\t\tMAIN-MENU");
		System.out.println("======================================================");
		System.out.println("[1] View Employee Schedule");
		System.out.println("[2] Assign Employee to a Shift");
		System.out.println("[3] Update / Remove Employee");
		System.out.println("[4] Search Employee");
		System.out.println("[5] Daily Summary Report");
		System.out.println("[6] Exit");

		System.out.print("\nEnter your Choice: ");
		try {
			input = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			input = 0;
		}
		System.out.println();

		switch (input) {
		case 1:
			displaySchedule();
			break;
		case 2:
			assignEmployee();
			break;
		case 3:
			updateEmployee();
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
			System.out.println("> Invalid Input!");
		}
	}// menuController() method

	public static void searchEmployee() {
		String daySlotHeader[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String shiftSlotHeader[] = { "MORNING", "AFTERNOON", "EVENING" };
		boolean hasSchedule = false;
		System.out.println("===================================================");
		System.out.println("\t\t SEARCH EMPLOYEE");
		System.out.println("===================================================");

		System.out.print("Enter Employee ID to Search: ");
		String searchName = scan.nextLine();

		String matchingUser = dupeChecker(searchName);

		if (matchingUser != null) {
			System.out.println("\n--- Employee Found! ---");
			System.out.println("Name: "
					+ matchingUser.substring(matchingUser.lastIndexOf("-") + 2, matchingUser.lastIndexOf("(") - 1));
			System.out.println(
					"Role: " + matchingUser.substring(matchingUser.lastIndexOf("(") + 1, matchingUser.length() - 1));
			System.out.println("-----------------------");
			System.out.println("Current Schedule:");

			int totalHours = 0;
			// Loop through the main Array
			for (int sh = 0; sh < mallSchedule.length; sh++) {
				for (int row = 0; row < mallSchedule[sh].length; row++) {
					for (int col = 0; col < mallSchedule[sh][row].length; col++) {
						hasSchedule = true;
						if (mallSchedule[sh][row][col] != null && mallSchedule[sh][row][col].contains(searchName)) {
							System.out.println("> " + daySlotHeader[sh] + ", " + shiftSlotHeader[row] + " shift.");
							if (row == 0)
								totalHours += 4;
							else if (row == 1)
								totalHours += 2;
							else if (row == 2)
								totalHours += 6;

						}
					}
				}
			}

			if (!hasSchedule) {
				System.out.println("No shifts assigned yet.");
			} else {

				System.out.println("-----------------------");
				System.out.println("> Total Working Hours: " + totalHours);

			}

		} else {
			System.out.println("\n Invalid: Employee \" " + searchName + "\" not found in the database.");
		}

		// Short pause before going back to menu
		System.out.println("\nPress Enter to return to Main Menu...");
		scan.nextLine();
	}

	/*
	 * XXX: DO NOT HOLLOW PURPLE :: RELATED METHOD searchEmployee() Returns the
	 * earliest shift entry that contains the inputted ID.
	 */

	public static String dupeChecker(String target) {
		for (int sh = 0; sh < mallSchedule.length; sh++) {
			for (int row = 0; row < mallSchedule[sh].length; row++) {
				for (int col = 0; col < mallSchedule[sh][row].length; col++) {

					if (mallSchedule[sh][row][col] != null && mallSchedule[sh][row][col].startsWith(target)) {

						return mallSchedule[sh][row][col];

					}
				}
			}
		}
		return null;
	}

	// Overloaded dupeChecker: Takes in specific layer to search. Used in duplicate
	// checking in assignEmployee();
	public static String dupeChecker(String target, int dayIndex) {

		for (int row = 0; row < mallSchedule[dayIndex].length; row++) {
			for (int col = 0; col < mallSchedule[dayIndex][row].length; col++) {

				if (mallSchedule[dayIndex][row][col] != null && mallSchedule[dayIndex][row][col].startsWith(target)) {

					return mallSchedule[dayIndex][row][col];

				}
			}
		}
		return null;
	}

	public static void removeEmployee() {
		System.out.println("Enter Name or ID: ");
		String nameForRemoval = scan.nextLine();
		for (int i = 0; i < mallSchedule.length; i++) {
			for (int j = 0; j < mallSchedule[i].length; j++) {
				for (int k = 0; k < mallSchedule[i][j].length; k++) {
					if (mallSchedule[i][j][k] != null) {
						if (mallSchedule[i][j][k].toLowerCase().contains(nameForRemoval.toLowerCase())) {
							mallSchedule[i][j][k] = null;
							break;
						}
					}
				}
			}
		}
	}
	
	public static void updateEmployee() {
		System.out.println("Enter Name or ID: ");
		String nameForRemoval = scan.nextLine();
		
		System.out.println("Enter Name or ID replace: ");
		String nameForUpdate = scan.nextLine();
		for (int i = 0; i < mallSchedule.length; i++) {
			for (int j = 0; j < mallSchedule[i].length; j++) {
				for (int k = 0; k < mallSchedule[i][j].length; k++) {
					if (mallSchedule[i][j][k] != null) {
						if (mallSchedule[i][j][k].toLowerCase().contains(nameForRemoval.toLowerCase())) {
							mallSchedule[i][j][k] = nameForUpdate;
							break;
						}
					}
				}
			}
		}
	}

	public static void exit() {
		// Menu test delete nalang
		System.out.println("Thank you for using the Mall Employee Scheduling System!");
		System.out.println("Program Terminated");
	}// exit() method
}
