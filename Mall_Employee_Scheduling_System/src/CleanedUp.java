import java.util.Scanner;

public class CleanedUp {

	private static Scanner scan = new Scanner(System.in);

	// DAYS (COLUMNS)
	private static String[] daySlotHeader = { "MON", "TUE", "WED", "THU", "FRI", "SAT" };

	// SHIFTS (ROWS)
	private static String[] shiftSlotHeader = { "M", "A", "E" };

	// EMPLOYEES: {ID, Name, Role}
	private static String[][] employees = { { "DR", "Who?", "Doctor" }, { "ENGY", "Dell", "Engineer" },
			{ "SLKS", "Hornet", "Security" }, { "CRUS", "John Dungeon", "Crusader" } };

	// mallSchedule[employee][shift][day]
	private static String[][][] mallSchedule = new String[employees.length][shiftSlotHeader.length][daySlotHeader.length];

	private static int input;

	public static void main(String[] args) {

		System.out.println("===================================================");
		System.out.println("          MALL EMPLOYEE SCHEDULING SYSTEM          ");
		System.out.println("===================================================");
		System.out.println("Legend: M = Morning | A = Afternoon | E = Evening");

		System.out.println("\nPress Enter to continue...");
		scan.nextLine();

		menuController();
	}

	// ================= DISPLAY =================

	public static void displaySchedule() {

		System.out.println("\n=================================================");
		System.out.println("\t\tEMPLOYEE SCHEDULE");
		System.out.println("=================================================");
		System.out.println("DAY    MORNING (M)    AFTERNOON (A)    EVENING (E)");

		for (int day = 0; day < daySlotHeader.length; day++) {

			System.out.print(daySlotHeader[day]);

			for (int shift = 0; shift < shiftSlotHeader.length; shift++) {

				String output = "-";

				for (int emp = 0; emp < employees.length; emp++) {
					if (mallSchedule[emp][shift][day] != null) {
						output = employees[emp][1] + " (" + employees[emp][2] + ")";
						break;
					}
				}
				System.out.print("    " + output);
			}
			System.out.println();
		}
		System.out.println();
	}

	// ================= ASSIGN SHIFT =================
	public static void assignEmployee() {
		int employeeIndex = -1;
		int dayIndex = -1;
		int shiftIndex = -1;
		String shiftEntry = null;

		System.out.println("\n>> Assigning Employee to Shift:");

		// ===== Employee Selection =====
		while (true) {
			System.out.print("> Enter Employee ID (or 'cancel'): ");
			String empID = scan.nextLine();
			if (empID.equalsIgnoreCase("cancel"))
				return;

			int i = 0;
			boolean found = false;
			while (i < employees.length) {
				if (empID.equalsIgnoreCase(employees[i][0])) {
					employeeIndex = i;
					shiftEntry = employees[i][1] + " (" + employees[i][2] + ")";
					found = true;
					break;
				}
				i++;
			}

			if (found)
				break;
			System.out.println("> Employee not found. Try again.");
		}

		// ===== Day Selection =====
		while (true) {
			System.out.print("> Enter Day (MON–SAT) or 'cancel': ");
			String dayInput = scan.nextLine().toUpperCase();
			if (dayInput.equalsIgnoreCase("cancel"))
				return;

			int d = 0;
			dayIndex = -1;
			while (d < daySlotHeader.length) {
				if (daySlotHeader[d].equals(dayInput)) {
					dayIndex = d;
					break;
				}
				d++;
			}

			if (dayIndex != -1)
				break;
			System.out.println("> Invalid day. Try again.");
		}

		// ===== Shift Selection =====
		while (true) {
			System.out.print("> Enter Shift (M/A/E) or 'cancel': ");
			String shiftInput = scan.nextLine().toUpperCase();
			if (shiftInput.equalsIgnoreCase("cancel"))
				return;

			int s = 0;
			shiftIndex = -1;
			while (s < shiftSlotHeader.length) {
				if (shiftSlotHeader[s].equals(shiftInput)) {
					shiftIndex = s;
					break;
				}
				s++;
			}

			if (shiftIndex != -1)
				break;
			System.out.println("> Invalid shift. Try again.");
		}

		// ===== Duplicate Check =====
		int emp = 0;
		while (emp < employees.length) {
			if (mallSchedule[employeeIndex][shiftIndex][dayIndex] != null) {
				System.out.println("> Employee already assigned to this shift!");
				return;
			}
			emp++;
		}

		// ===== Assign to First Empty Slot =====
		mallSchedule[employeeIndex][shiftIndex][dayIndex] = shiftEntry;
		System.out.println("> " + shiftEntry + " assigned to " + shiftSlotHeader[shiftIndex] + " on "
				+ daySlotHeader[dayIndex] + " successfully!\n");
	}

	// ================= MENU =================

	public static void menuController() {

		while (true) {
			System.out.println("===================================================");
			System.out.println("\t\tMAIN MENU");
			System.out.println("===================================================");
			System.out.println("[1] View Employee Schedule");
			System.out.println("[2] Assign Employee to Shift");
			System.out.println("[3] Exit");

			System.out.print("Enter choice: ");
			input = scan.nextInt();
			scan.nextLine(); // FIXED BUFFER ISSUE

			switch (input) {
			case 1:
				displaySchedule();
				break;
			case 2:
				assignEmployee();
				break;
			case 3:
				exit();
				return;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	public static void exit() {
		System.out.println("Thank you for using the system!");
		System.out.println("Program terminated.");
	}
}
