import java.util.Scanner;

/**
 * Version 2.8.1 palitan tong version everytime na mag edit po kayo! Always
 * refresh in Codiva!!
 * 
 * We will assist as much as we can! "ken"
 */
public class Final {
	/**
	 * Properties: This are the variables that will be used throughout the software
	 */
	private static Scanner scan = new Scanner(System.in);

	// Array for Days
	private static String daySlotHeader[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
	// Array for Shifts
	private static String shiftSlotHeader[] = { "M", "A", "E" };
	// Mall Schedule
	private static String mallSchedule[][][] = new String[6][3][10];

	// Variables used in Methods
	static final int timeSlot = 3;
	static int shifts;
	static String userInput = "";

	// user input used in Menu
	private static int input = 0;

	/**
	 * ======================= THE MAIN METHOD =======================
	 * <p>
	 * This is our entry point of the program, we want to ask the user to press
	 * enter first before the program begins, by using the scanner nextLine()
	 * method.
	 */
	public static void main(String[] args) {
		System.out.println("=====================================================================================");
		System.out.println("                           MALL EMPLOYEE SCHEDULING SYSTEM                           ");
		System.out.println("=====================================================================================");
		System.out.println("\n                       <<Press Enter to Begin Setup Process>>");
		scan.nextLine();

		while (true) {
			displaySchedule();
			menuController();
		}
	}// main() method

	/**
	 * ======================= DISPLAY SCHEDULE METHOD =======================
	 * <p>
	 * This method displays the overall schedule of the Employees, Including the
	 * total employees per day.
	 */
	public static void displaySchedule() {
		int spaceLength = 0; // This is used to control the spacing per column

		// Header Design
		System.out.println("=====================================================================================");
		System.out.println("                                 EMPLOYEE SCHEDULE                                   ");
		System.out.println("=====================================================================================");
		System.out.println("DAY        |   MORNING(M)               AFTERNOON(A)             EVENING(E)");
		System.out.println("-------------------------------------------------------------------------------------");

		// =====================
		// Loop through each DAY
		// =====================
		for (int day = 0; day < mallSchedule.length; day++) {

			int maxRows = 1; // How many stacked rows need to be printed

			// ----------------------------------------------------
			// FIND THE MAXIMUM NUMBER OF EMPLOYEES IN ANY SHIFT
			// FOR THIS SPECIFIC DAY
			// ----------------------------------------------------
			for (int shift = 0; shift < mallSchedule[day].length; shift++) {

				int count = 0; // Counts employees in current shift

				// this counts the non-null employees in this shift
				for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {
					if (mallSchedule[day][shift][emp] == null)
						continue; // skips the empty slots
					count++;
				}

				// Updates the maxRows if this shift has more employees
				if (count > maxRows) {
					maxRows = count;
				}
			}

			// This print STACKED ROWs
			for (int row = 0; row < maxRows; row++) {

				// Print day only on first row
				if (row == 0) {
					System.out.print(daySlotHeader[day]);
					for (int space = 1; space <= 11 - daySlotHeader[day].length(); space++) {
						System.out.print(" ");
					}
					System.out.print("|   "); // test |
				} else {
					System.out.print("           |   "); // 12 spaces
				}

				// Loop through SHIFTs
				for (int shift = 0; shift < mallSchedule[0].length; shift++) {

					int index = 0;
					String output = " ";
					// This find EMPLOYEE for Row

					for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {

						if (mallSchedule[day][shift][emp] == null) {
							output = "-";
							spaceLength = 25 - output.length();
							continue;
						}
						if (index == row) {
							output = mallSchedule[day][shift][emp]; // print the actual assigned employee
							spaceLength = 25 - output.length();
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
			System.out.println("-------------------------------------------------------------------------------------");
		}

		// TOTAL EMPLOYEES PER DAY
		System.out.println("\nTOTAL EMPLOYEES PER DAY:");

		for (

				int day = 0; day < mallSchedule.length; day++) {

			int total = 0;

			for (int shift = 0; shift < mallSchedule[day].length; shift++) {
				for (int emp = 0; emp < mallSchedule[day][shift].length; emp++) {

					if (mallSchedule[day][shift][emp] == null)
						continue;
					total++;
				}
			}
			System.out.println("\u2022 " + daySlotHeader[day] + ": " + total);
		}
		System.out.println();
	}// > displaySchedule() method

	/**
	 * ======================= THE MAIN MENU METHOD =======================
	 * <p>
	 * This method is used to display the menu with the following options, view the
	 * employee, assign or remove the employee, search the employee and exit the
	 * system. I also included the daily summary report inside the main menu so that
	 * user can quickly see it.
	 */
	public static void menuController() {
		System.out.println("=====================================================================================");
		System.out.println("                                    MAIN-MENU");
		System.out.println("=====================================================================================");
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
			subMenu();
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
	}// > menuController() method

	/**
	 * ======================= THE ASSIGN EMPLOYEE METHOD =======================
	 * <p>
	 * This method is used to assign employees to a selected day shift and then
	 * updates the schedule, and also allows multiple employees in one transaction
	 */
	public static void assignEmployee() {

		int shiftIndex, dayIndex;

		System.out.println("=====================================================================================");
		System.out.println("                                 ASSIGN EMPLOYEE");
		System.out.println("=====================================================================================");
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
		boolean assignAgain = true;

		while (assignAgain) {

			boolean assigned = false;

			// --- Step 4: Check for duplicate on the same day ---
			String dupeValue = dupeChecker(empID + " -", dayIndex);

			if (dupeValue == null) {

				for (int i = 0; i < mallSchedule[dayIndex][shiftIndex].length; i++) {

					if (mallSchedule[dayIndex][shiftIndex][i] == null
							|| mallSchedule[dayIndex][shiftIndex][i].equals("-")) {

						mallSchedule[dayIndex][shiftIndex][i] = entry;
						System.out.println(
								"-------------------------------------------------------------------------------------");
						System.out.println("> " + empID + " - " + empName + " was successfully assigned to "
								+ shiftSlotHeader[shiftIndex] + " shift on " + daySlotHeader[dayIndex] + ".");

						assigned = true;
						break;
					}
				}

				if (!assigned) {
					System.out.println("> ERROR: All " + shiftSlotHeader[shiftIndex] + " shift slots on "
							+ daySlotHeader[dayIndex] + " are already filled.");
				}

			} else {

				System.out
						.println("> ERROR: " + dupeValue + " is already assigned on " + daySlotHeader[dayIndex] + ".");
			}

			// --- Ask user if they want to assign again ---
			// - Ken

			while (true) {
				System.out.print("\nDo you want to assign again? Y or N: ");
				String choice = scan.nextLine().toUpperCase();
				switch (choice) {

				case "Y":
					System.out.println();
					assignEmployee();
					break;

				case "N":
					System.out.println();
					menuController();
					;
					return; // Go back to where exit() was called

				default:
					System.out.println("Invalid Input! Please enter Y or N.");
				}
			}

		}
	}// assignEmployee() method

	/**
	 * ======================= DAY VALIDATION METHOD =======================
	 * <p>
	 * This method is used in the Assign Employee Method, The goal is to ask the
	 * user the day.
	 */
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
	}// dayValidation() method

	/**
	 * ======================= SHIFT VALIDATION METHOD =======================
	 * <p>
	 * This method is used in the Assign Employee Method, The goal is to ask the
	 * user the shift.
	 */
	private static int shiftValidation() {
		int shiftIndex = -1;
		while (shiftIndex == -1) {
			System.out.print("Enter Shift:");
			System.out.println("\n    \u2022 [M] Morning");
			System.out.println("    \u2022 [A] Afternoon");
			System.out.println("    \u2022 [E] Evening");
			System.out.print("\nEnter your Choice: ");
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
	}// shiftValidation() method

	/**
	 * ================ SEARCHING THE EMPLOYEE METHOD =======================
	 * <p>
	 * This goal of the method is to search the ID or NAME of an Employee, Then it
	 * will display the Assigned day, shift, roles, total working hours.F
	 */
	public static void searchEmployee() {
		String daySlotHeader[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		String shiftSlotHeader[] = { "MORNING", "AFTERNOON", "EVENING" };
		boolean hasSchedule = false;
		System.out.println("=====================================================================================");
		System.out.println("                                  SEARCH EMPLOYEE");
		System.out.println("=====================================================================================");

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
	}// searchEmployee() method

	/**
	 * This is a submenu for Update and Remove
	 */
	public static void subMenu() {
		System.out.println("=====================================================================================");
		System.out.println("                                    UPDATE OR REMOVE EMPLOYEE");
		System.out.println("=====================================================================================");
		System.out.println("[1] Update Employee");
		System.out.println("[2] Remove Employee");
		System.out.print("\nEnter your Choice: ");
		try {
			input = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			input = 0;
		}
		System.out.println();

		switch (input) {
		case 1:
			updateEmployee();
			break;
		case 2:
			removeEmployee();
			break;
		default:
			System.out.println("> Invalid Input!");
		}
	}

	public static void removeEmployee() {
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.print("Enter Name or ID: ");
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
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.print("Enter Employee ID to Update: ");
		String empID = scan.nextLine();

		// Check if employee exists first using paz dupeChecker
		String existing = dupeChecker(empID + " -");

		if (existing == null) {
			System.out.println("> ERROR: Employee not found!");
			return;
		}

		// Extract old name and role (optional display)
		System.out.println("\nCurrent Record: " + existing);

		System.out.print("Enter New Employee Name: ");
		String newName = scan.nextLine();

		System.out.print("Enter New Role: ");
		String newRole = scan.nextLine();

		String updatedEntry = empID + " - " + newName + " (" + newRole + ")";

		boolean updated = false;

		// Replace ALL occurrences
		for (int i = 0; i < mallSchedule.length; i++) {
			for (int j = 0; j < mallSchedule[i].length; j++) {
				for (int k = 0; k < mallSchedule[i][j].length; k++) {

					String value = mallSchedule[i][j][k];

					if (value != null) {

						boolean match = true;
						String target = empID + " -";

						// If value is shorter than target, it can't match
						if (value.length() < target.length()) {
							match = false;
						} else {
							for (int x = 0; x < target.length(); x++) {
								if (value.charAt(x) != target.charAt(x)) {
									match = false;
									break;
								}
							}
						}

						if (match) {
							mallSchedule[i][j][k] = updatedEntry;
						}
					}

				}
			}
		}

		if (updated) {
			System.out.println("\n> Employee successfully updated!");
		}
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

	public static void exit() {

		while (true) {
			System.out.print("Are you sure you want to exit? Y or N: ");
			String exitInput = scan.nextLine().toUpperCase();

			switch (exitInput) {

			case "Y":
				System.out.println("\nThank you for using the Mall Employee Scheduling System!");
				System.out.println("Program Terminated");
				// I couldn't figure out a way to terminate this because this method only prints
				// not terminate!
				// But i found this method in the system
				System.exit(0);
				break;

			case "N":
				System.out.println();
				return; // Go back to where exit() was called

			default:
				System.out.println("Invalid Input! Please enter Y or N.");
			}
		}

	} // exit() method

}
