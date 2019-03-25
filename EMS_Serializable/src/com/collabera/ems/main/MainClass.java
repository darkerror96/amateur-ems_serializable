/**
 * 
 */
package com.collabera.ems.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.collabera.ems.model.Address;
import com.collabera.ems.model.Department;
import com.collabera.ems.model.Employee;
import com.collabera.ems.model.Manager;
import com.collabera.ems.model.Gender;

/**
 * @author rutpatel
 *
 */
public class MainClass {

	private static Map<Integer, Employee> empDb = new HashMap<Integer, Employee>();
	private static Map<Integer, Integer> empMap = null;
	private static final String EMSdb = "EMSdb.txt", EMSmap = "EMSmap.txt";
	// private static final String E = "\033[31m", S = "\033[32m", D = "\033[0m";
	private static final String E = "", S = "", D = "";

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		boolean loop = true;

		if (new File(EMSdb).exists() & new File(EMSmap).exists()) {
			try (ObjectInputStream oisDb = new ObjectInputStream(new FileInputStream(new File(EMSdb)));
					ObjectInputStream oisMap = new ObjectInputStream(new FileInputStream(new File(EMSmap)));) {
				if (oisDb.toString() != null & oisMap.toString() != null) {
					empDb = (HashMap<Integer, Employee>) oisDb.readObject();
					empMap = (HashMap<Integer, Integer>) oisMap.readObject();

					printEagles();

					System.out.println("-----------------------------------------------");
					System.out.println("  Welcome to Employee Management System (EMS)");
					System.out.println("-----------------------------------------------");

				} else {
					System.out.println("Found Files are Empty. Database can't be loaded...");
					loop = false;
				}
			} catch (FileNotFoundException e) {
				loop = false;
				System.out.println(E + e.getMessage() + D);
			} catch (IOException e) {
				loop = false;
				System.out.println(E + e.getMessage() + D);
			} catch (ClassNotFoundException e) {
				loop = false;
				System.out.println(E + e.getMessage() + D);
			}
		} else {
			loop = false;
			System.out.println(E + "File not found. Database can't be loaded..." + D);
		}

		while (loop) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				System.out.println(e1.getMessage());
			}

			System.out.println("-----------------------------------------------");
			System.out.println("        Employee Management System Menu        ");
			System.out.println("-----------------------------------------------");

			System.out.println("C) Create Employee");
			System.out.println("R) Retrieve Employee");
			System.out.println("U) Update Employee");
			System.out.println("D) Delete Employee");
			System.out.println("P) Print All Employee");
			System.out.println("T) Print Manager's Team");
			System.out.println("N) Sort Employee by Name");
			System.out.println("S) Save");
			System.out.println("E) Exit");

			System.out.print("\nEnter your choice - ");

			String choice = sc.nextLine().trim().toUpperCase();
			System.out.println();
			switch (choice) {
			case "C":
				create(sc);
				break;

			case "R":
				search(sc);
				break;

			case "U":
				update(sc);
				break;

			case "D":
				delete(sc);
				break;

			case "P":
				printAll();
				break;

			case "N":
				sortByName();
				break;

			case "T":
				Employee emp = searchByManId(sc);
				if (emp != null) {
					if (emp.isManager()) {
						Manager man = (Manager) emp;
						System.out.println(S + "-------Team of Employee/s under " + man.getName() + "-------\n" + D);
						for (Employee empTemp : man.getTeam().values()) {
							System.out.println(empTemp);
						}
						System.out.println();
					} else {
						System.out.println(E + "Found Employee is not Manager..." + D);
					}
				}
				System.out.println();
				break;

			case "S":

				try (ObjectOutputStream oosDb = new ObjectOutputStream(new FileOutputStream(new File(EMSdb)));
						ObjectOutputStream oosMap = new ObjectOutputStream(new FileOutputStream(new File(EMSmap)));) {
					oosDb.writeObject(empDb);
					oosMap.writeObject(empMap);
					System.out.println(S + "Employee Database saved..." + D);
					System.out.println();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

			case "E":

				try (ObjectOutputStream oosDb = new ObjectOutputStream(new FileOutputStream(new File(EMSdb)));
						ObjectOutputStream oosMap = new ObjectOutputStream(new FileOutputStream(new File(EMSmap)));) {
					oosDb.writeObject(empDb);
					System.out.println(S + "Employee Database Updated...(EMS.db)" + D);

					oosMap.writeObject(empMap);
					System.out.println(S + "Employee-Manager Map Updated...(EMS.map)" + D);
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("\n---------------------------------------------");
				System.out.println("            EMS System Closed");
				System.out.println("---------------------------------------------");
				loop = false;
				break;

			default:
				System.out.println(E + "Invalid input..." + D);
				break;
			}
		}
		sc.close();
	}

	public static void sortByName() {

		System.out.println("---------------Sorting Employee/s by Length of Name---------------\n");

		// Set<Employee> sortName = new HashSet<Employee>();
		Set<Employee> sortName = new TreeSet<Employee>();
		for (Employee e : empDb.values()) {
			sortName.add(e);
		}
		for (Employee e : sortName) {
			System.out.println(e);
		}
		System.out.println();
	}

	public static void printEagles() {
		System.out.println( // Eagle Art
				"                    __,__r========qL,\n" + "                 ,m**`~'            XW;\n"
						+ "          ____Lm/'      _mmmm    jW@+===m___,\n"
						+ "    .m+*f~TX7''|    `Y*X@X7~MMLL          ._JXmL,\n"
						+ "  jf~'    `YM#:~      !*##@ d j@~Y!`     ~~~+m_\n"
						+ " Z            M       `Nd*=+5+      -====L__ !M-\n"
						+ "m!            `Y=;m_,   ~Yf~~               YL`W;\n"
						+ "|        .mmmmmqrL_XXX~*==mL,                 Y#|\n"
						+ "|  ,Z~~~~~'        ~~~~    X#         -==L__,  !#,\n"
						+ "| |tL             ____WY*f~~                `~qd#W\n"
						+ "V,| `~~Y*+mL_,  :f~~ ~                   q_    V#b\n"
						+ "`b|`         Y+-W;                        `\\\\,  _#\n"
						+ "  V              N,  m                 YL,   `\\L#@\n"
						+ "                  N  #        EAGLES     YL,   `##,\n"
						+ "                 .P  #                     `\\   V#|\n"
						+ "               ../   #           ^           YL |W\n"
						+ "              -P     #    :stuck_out_tongue:  ;  ||\n"
						+ "              Z'     #   :stuck_out_tongue:  :'   #,\n"
						+ "          _ _Wb    jWD  jf   /    |b           V@*\n"
						+ "        j+N##8#4rt8#~.,jf    |    |#       b   |W\n"
						+ "     .r*'m8####q#P8###*'    W    :##       V;  d|\n"
						+ "    /K_jWK#X###~#|###mW#   :stuck_out_tongue:  j|\n"
						+ "   !TGW###8#m#K7q=WWmd##mmm#m.Z8###!    #W  |b |\n"
						+ "   /4#@##W#+##P:WZWMZ*W=m##WMN|WZWP:#;m,d#W;Dd |\n"
						+ "  W*~~~M#W!KWjW5!!/WV##N7j#KXPNMX#DW###b###D#Xb|\n"
						+ " !'    d!JXXXW!`!Ldm@#bd#WW8K)X|X#8#8#K##V#!K5|#\n"
						+ "      d@f~  jf.Z\\/Vb*Y+W#j(WMZPrMM#@##K#@##;WWj##,\n"
						+ "          ~m*~'   '#`\\8#W4KW#qX@VW##W#8##8#rNWK###G-\n"
						+ "          `'       #`8*=#YWWK/b+WW#W#*#MWm8*KKd#M#**m,\n"
						+ "                   #Z'  VW#f'~M+NM#####K#8@WLMZKX#W#+M;\n"
						+ "                            ~` *YM#######@*)P/+DK####m#\n"
						+ "                                 `'*##fXVPX|Vr~##Z#W@N@\n"
						+ "                                    `WL_d**DfWX#8#WK##*b\n"
						+ "                                              `W###@#N#/b\n"
						+ "                                               .#mj#X~YM#\n"
						+ "                                               ~`#WdD~  Y!\n"
						+ "                                               `'_|#f--\n"
						+ "                                                   *");
	}

	public static void printAll() {

		System.out.println(S + "\n<-> " + empDb.size() + " Entry in Employee Database <->\n" + D);
		if (empDb.size() > 0) {
			for (Employee emp : empDb.values()) {
				System.out.println(emp);
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
			System.out.println();
		}
	}

	public static void printAllName() {

		if (empDb.size() > 0) {
			System.out.println("(Id) -   Name");
			for (Employee emp : empDb.values()) {
				if (emp.isManager()) {
					System.out.println(S + "(" + emp.getEmpId() + ")" + D + "  - " + emp.getName() + " (Manager)");
				} else {
					System.out.println(S + "(" + emp.getEmpId() + ")" + D + "  - " + emp.getName());
				}
			}
			System.out.println();
		}
	}

	public static void printEmployeesName() {

		if (empDb.size() > 0) {
			System.out.println("---Employee---");
			System.out.println("(Id) -   Name");
			for (Employee emp : empDb.values()) {
				if (!emp.isManager()) {
					System.out.println(S + "(" + emp.getEmpId() + ")" + D + "  - " + emp.getName());
				}
			}
			System.out.println();
		}
	}

	public static void printManagersName() {

		if (empDb.size() > 0) {
			System.out.println("---Manager---");
			System.out.println("(Id) -   Name");
			for (Employee emp : empDb.values()) {
				if (emp.isManager()) {
					System.out.println(S + "(" + emp.getEmpId() + ")" + D + "  - " + emp.getName());
				}
			}
			System.out.println();
		}
	}

	public static void printEmployeeManager() {

		if (empDb.size() > 0) {
			System.out.println("---Employee---");
			System.out.println("(Id) -   Name");
			for (Employee emp : empDb.values()) {
				if (!emp.isManager()) {
					System.out.println(S + "(" + emp.getEmpId() + ")" + D + "  - " + emp.getName()
							+ " (Current Manager - (" + emp.getReportTo() + ")"
							+ ((Manager) empDb.get(emp.getReportTo())).getName() + ")");
				}
			}
			System.out.println();
		}
	}

	public static void create(Scanner sc) {

		String name = null, email = null, jobTitle = null;
		int age = 0, ssn = 0, gender = 0, department = 0;
		long contactNo = 0;
		double salary = 0;
		boolean isManager = false;
		Gender gen = null;
		Department dept = null;
		Address address = null;

		// --------------------Employee Name--------------------
		System.out.println("\n----------Enter Employee Details----------\n");
		System.out.print("Enter Employee Name - ");
		while (true) {
			name = sc.nextLine().trim();
			if (validateName(name)) {
				break;
			} else {
				System.out.print("Invalid input. Enter Employee Name in correct format (FirstName LastName) - ");
			}
		}
		// --------------------Employee EMail--------------------
		int nameFound = 0;
		for (Employee e : empDb.values()) {
			if (e.getName().equals(name)) {
				nameFound++;
			}
		}
		if (nameFound == 0) {
			email = name.toLowerCase().replaceAll("\\s", "") + "@colla.com";
		} else {
			email = name.toLowerCase().replaceAll("\\s", "") + nameFound + "@colla.com";
		}

		// --------------------Employee Age--------------------
		while (true) {
			System.out.print("Enter Employee Age - ");
			String stringAge = sc.nextLine().trim();

			try {
				age = Integer.parseInt(stringAge);
			} catch (Exception e) {
				System.out.println(E + "Invalid input. Age must be in between 18 to 66. Try again." + D);
				continue;
			}
			if (age < 18 || age > 66) {
				System.out.println(E + "Invalid input. Age must be in between 18 to 66. Try again." + D);
				continue;
			}
			break;
		}

		// --------------------Employee Gender--------------------
		System.out.println();
		for (Gender g : Gender.values()) {
			System.out.print(g.ordinal() + ")" + g + " ");
		}
		System.out.println();
		while (true) {
			System.out.print("Select Employee Gender - ");
			String stringGen = sc.nextLine().trim();

			try {
				gender = Integer.parseInt(stringGen);
			} catch (Exception e) {
				System.out.println(E + "Invalid input. Gender must be 0 / 1 / 2. Try again." + D);
				continue;
			}
			if (gender < 0 || gender > 2 || stringGen.length() != 1) {
				System.out.println(E + "Invalid input. Gender must be 0 / 1 / 2. Try again." + D);
				continue;
			}
			gen = Gender.values()[gender];
			break;
		}

		// --------------------Employee Contact No--------------------
		System.out.println();
		while (true) {
			System.out.print("Enter Employee Contact No - ");
			String stringContactNo = sc.nextLine().trim();

			try {
				contactNo = Long.parseLong(stringContactNo);
			} catch (Exception e) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			}
			if (contactNo < 0 || stringContactNo.length() != 10 || stringContactNo.startsWith("0")) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			}
			break;
		}

		// --------------------Employee Address--------------------
		System.out.println("\n--------Employee Address--------\n");

		System.out.print("Enter Employee Street Address - ");
		String streetAddress = sc.nextLine().trim();

		System.out.print("Enter Employee City - ");
		String city = null;
		while (true) {
			city = sc.nextLine().trim();
			if (validateNamingConvention(city)) {
				break;
			} else {
				System.out.print("Invalid input. Enter Employee City correctly - ");
			}
		}

		System.out.print("Enter Employee State - ");
		String state = null;
		while (true) {
			state = sc.nextLine().trim();
			if (validateNamingConvention(state)) {
				break;
			} else {
				System.out.print("Invalid input. Enter Employee State correctly - ");
			}
		}

		System.out.print("Enter Employee Country - ");
		String country = null;
		while (true) {
			country = sc.nextLine().trim();
			if (validateNamingConvention(country)) {
				break;
			} else {
				System.out.print("Invalid input. Enter Employee Country correctly - ");
			}
		}

		// --------------------Employee ZipCode--------------------
		int zipCode = 0;
		while (true) {
			System.out.print("Enter Employee Zip Code - ");
			String stringZip = sc.nextLine().trim();

			try {
				zipCode = Integer.parseInt(stringZip);
			} catch (Exception e) {
				System.out
						.println(E + "Invalid input. Zipcode must be a positive integer with 5 digits. Try again." + D);
				continue;
			}
			if (zipCode < 0 || stringZip.length() != 5) {
				System.out
						.println(E + "Invalid input. Zipcode must be a positive integer with 5 digits. Try again." + D);
				continue;
			}
			break;
		}
		address = new Address(streetAddress, city, state, country, zipCode);

		System.out.println("\n--------------------------------\n");

		// --------------------Employee SSN--------------------
		while (true) {
			System.out.print("Enter Employee SSN - ");
			String stringSSN = sc.nextLine().trim();

			try {
				ssn = Integer.parseInt(stringSSN);
			} catch (Exception e) {
				System.out.println(E + "Invalid input. SSN must be a positive integer with 9 digits. Try again." + D);
				continue;
			}
			if (ssn < 0 || stringSSN.length() != 9) {
				System.out.println(E + "Invalid input. SSN must be a positive integer with 9 digits. Try again." + D);
				continue;
			}
			break;
		}

		// --------------------Employee JobTitle--------------------
		System.out.print("Enter Employee Job Title - ");
		while (true) {
			jobTitle = sc.nextLine().trim();
			if (validateNamingConvention(jobTitle)) {
				break;
			} else {
				System.out.print("Invalid input. Enter Employee Job Title correctly - ");
			}
		}

		// --------------------Employee Department--------------------
		System.out.println();
		for (Department d : Department.values()) {
			System.out.print(d.ordinal() + ")" + d + " ");
		}
		System.out.println();
		while (true) {
			System.out.print("Select Employee Department - ");
			String stringDept = sc.nextLine().trim();

			try {
				department = Integer.parseInt(stringDept);
			} catch (Exception e) {
				System.out.println(E + "Invalid input. Department must be 0 / 1 / 2. Try again." + D);
				continue;
			}
			if (department < 0 || department > 2 || stringDept.length() != 1) {
				System.out.println(E + "Invalid input. Department must be 0 / 1 / 2. Try again." + D);
				continue;
			}
			dept = Department.values()[department];
			break;
		}

		// --------------------Employee Salary--------------------
		System.out.println();
		while (true) {
			System.out.print("Enter Employee Salary - ");
			String stringSalary = sc.nextLine().trim();

			try {
				salary = Double.parseDouble(stringSalary);
			} catch (Exception e) {
				System.out.println(E + "Invalid input. Salary must be a positive integer. Try again." + D);
				continue;
			}
			if (salary < 0) {
				System.out.println(E + "Invalid input. Salary must be a positive integer. Try again." + D);
				continue;
			}
			break;
		}

		// --------------------Employee isManager--------------------
		System.out.println();
		String isMan;
		boolean flagM;
		do {
			System.out.print("Is Manager?(Y/N)");
			isMan = sc.nextLine().trim().toUpperCase();

			if (isMan.equals("Y") || isMan.equals("N")) {
				// --------------------Manager--------------------
				if (isMan.equals("Y")) {
					isManager = true;

					Manager man = new Manager(name, age, gen, contactNo, address, ssn, email, jobTitle, dept, salary, 0,
							isManager);
					empDb.put(man.getEmpId(), man);

					int employeeId;
					String strEmpId;
					String moreEmployees;
					boolean empExist = false;
					boolean flagMYN = false;
					boolean flagQ = false;

					do {
						System.out.println();
						System.out.print("Add Employee under this Manager? (Y/N)");
						moreEmployees = sc.nextLine().trim().toUpperCase();

						if (moreEmployees.equals("Y") || moreEmployees.equals("N")) {
							flagMYN = false;
							if (moreEmployees.equals("Y")) {
								HashMap<Integer, Employee> team = (HashMap<Integer, Employee>) man.getTeam();

								// ----------Display only Employee Details to Manager----------
								printEmployeesName();

								do {
									System.out.print("Enter Employee Id (or Q to quit) - ");
									strEmpId = sc.nextLine().trim();
									if (strEmpId.toUpperCase().equals("Q")) {
										flagQ = false;
									} else {
										try {
											employeeId = Integer.parseInt(strEmpId);
											empExist = empDb.containsKey(employeeId);
											if (empExist) {
												if (empDb.get(employeeId).isManager()) {
													System.out.println(empDb.get(employeeId));
													System.out.println(
															E + "Found Manager can't be assigned under Teams...\n" + D);
												} else {
													team.put(employeeId, empDb.get(employeeId));
													empMap.put(employeeId, man.getEmpId());
													empDb.get(employeeId).setReportTo(man.getEmpId());

													System.out
															.println(S + "Employee(" + employeeId + ") added...\n" + D);
												}
											} else {
												System.out.println(E + "Entered Employee Id doesn't exists..." + D);
											}
										} catch (Exception e) {
											System.out.println(E
													+ "Invalid input. Employee Id must be a positive integer. Try again."
													+ D);
										}
										flagQ = true;
									}
								} while (flagQ);
							}
						} else {
							System.out.println(E + "Invalid Input. Enter Y/N only!!!" + D);
							flagMYN = true;
						}
					} while (flagMYN);
					System.out.println(S + "\n--------------Manager Created--------------\n" + D + man.toString());
					System.out.println();
				}
				// --------------------Employee--------------------
				else if (isMan.equals("N")) {
					isManager = false;

					Employee emp = new Employee(name, age, gen, contactNo, address, ssn, email, jobTitle, dept, salary,
							0, isManager);

					empDb.put(emp.getEmpId(), emp);

					System.out.println(S + "\n--------------Employee Created--------------\n" + D + emp.toString());
					System.out.println();

				}
				flagM = false;
			} else {
				System.out.println(E + "Invalid Input. Enter Y/N only!!!" + D);
				flagM = true;
			}
		} while (flagM);

		name = null;
		email = null;
		jobTitle = null;
		age = 0;
		contactNo = 0;
		ssn = 0;
		gender = 0;
		department = 0;
		salary = 0;
		isManager = false;
		gen = null;
		dept = null;
		address = null;
	}

	public static boolean validateName(String name) {
		if (name.length() == 0 || name.split(" ").length != 2) {
			return false;
		} else {
			String regx = "[^a-zA-Z\\s]";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(name);
			return !matcher.find();
		}
	}

	public static boolean validateNamingConvention(String place) {
		if (place.length() == 0) {
			return false;
		} else {
			String regx = "[^a-zA-Z\\s]";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(place);
			return !matcher.find();
		}
	}

	public static void search(Scanner sc) {

		boolean loop = true;
		while (loop) {

			System.out.println("-----------------------------------------------");
			System.out.println("            Search Employee Details");
			System.out.println("-----------------------------------------------");

			System.out.println("1) Search Employee by Id");
			System.out.println("2) Search Employee by Name");
			System.out.println("3) Search Employee by SSN");
			System.out.println("4) Search Employee by Contact Number");
			System.out.println("5) Exit");

			System.out.print("\nEnter your choice - ");

			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1":
				searchByEmpId(sc);
				break;

			case "2":
				searchByName(sc);
				break;

			case "3":
				searchBySSN(sc);
				break;

			case "4":
				searchByContactNo(sc);
				break;

			case "5":
				System.out.println("\n---------------------------------------------");
				System.out.println("            EMS Search System Closed");
				System.out.println("---------------------------------------------");
				loop = false;
				break;

			default:
				System.out.println(E + "Invalid input..." + D);
				break;
			}
		}
	}

	public static Employee searchByEmpId(Scanner sc) {

		printAllName();
		while (true) {
			System.out.print("\nEnter Employee Id to search/update for (or Q to quit) - ");
			String strEmpId = sc.nextLine().trim();
			if (strEmpId.toUpperCase().equals("Q")) {
				return null;
			} else {
				int id = 0;
				try {
					id = Integer.parseInt(strEmpId);
				} catch (Exception e) {
					System.out.println(E + "Invalid input. Employee Id must be a positive integer. Try again." + D);
					continue;
				}
				if (id < 1) {
					System.out.println(E + "Invalid input. Employee Id must be a positive integer. Try again." + D);
					continue;
				} else {
					if (empDb.containsKey(id)) {
						System.out.println(empDb.get(id) + "\n");
						return empDb.get(id);
					} else {
						System.out.println(E + "Employee with entered Id doesn't exists...\n" + D);
						return null;
					}
				}
			}
		}
	}

	public static Employee searchByManId(Scanner sc) {

		printManagersName();
		while (true) {
			System.out.print("\nEnter Manager Id to search for - ");
			int id = 0;
			try {
				id = Integer.parseInt(sc.nextLine().trim());
			} catch (Exception e) {
				System.out.println(E + "Invalid input. Manager Id must be a positive integer. Try again." + D);
				continue;
			}
			if (id < 1) {
				System.out.println(E + "Invalid input. Manager Id must be a positive integer. Try again." + D);
				continue;
			} else {
				if (empDb.containsKey(id)) {
					System.out.println(empDb.get(id) + "\n");
					return empDb.get(id);
				} else {
					System.out.println(E + "Manager with entered Id doesn't exists...\n" + D);
					return null;
				}
			}
		}
	}

	public static void searchByName(Scanner sc) {

		boolean nameFound = false;
		System.out.print("\nEnter Employee Name to search for - ");
		String name = sc.nextLine().trim().toLowerCase();
		for (Employee e : empDb.values()) {
			if (e.getName().toLowerCase().equals(name) || e.getName().toLowerCase().contains(name)) {
				System.out.println(e);
				nameFound = true;
			}
		}
		if (!nameFound) {
			System.out.println(E + "Employee with entered Name doesn't exists..." + D);
		}
		System.out.println();
	}

	public static void searchByContactNo(Scanner sc) {

		while (true) {
			System.out.print("\nEnter Employee Contact Number to search for - ");
			long contactNo = 0;
			try {
				contactNo = Long.parseLong(sc.nextLine().trim());
			} catch (Exception e) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			}
			if (contactNo < 1 || String.valueOf(contactNo).length() != 10) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			} else {
				boolean contactNoFound = false;
				for (Employee e : empDb.values()) {
					if (e.getContactNo() == contactNo) {
						System.out.println(e);
						contactNoFound = true;
					}
				}
				if (!contactNoFound) {
					System.out.println(E + "Employee with entered Contact Number doesn't exists..." + D);
				}
				System.out.println();
				break;
			}
		}
	}

	public static void searchBySSN(Scanner sc) {

		while (true) {
			System.out.print("\nEnter Employee SSN to search for - ");
			int ssn = 0;
			try {
				ssn = Integer.parseInt(sc.nextLine().trim());
			} catch (Exception e) {
				System.out.println(E + "Invalid input. SSN must be a positive integer with 9 digits. Try again." + D);
				continue;
			}
			if (ssn < 1 || String.valueOf(ssn).length() != 9) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 9 digits. Try again." + D);
				continue;
			} else {
				boolean ssnFound = false;
				for (Employee e : empDb.values()) {
					if (e.getSsn() == ssn) {
						System.out.println(e);
						ssnFound = true;
					}
				}
				if (!ssnFound) {
					System.out.println(E + "Employee with entered SSN doesn't exists..." + D);
				}
				System.out.println();
				break;
			}
		}
	}

	public static void update(Scanner sc) {

		boolean loop = true;
		while (loop) {

			System.out.println("\nSelect Employee Id from below table to Update Employee Details\n");

			Employee emp = searchByEmpId(sc);
			if (emp == null) {
				break;
			}

			System.out.println("-----------------------------------------------");
			System.out.println("            Update " + emp.getName() + " Deatils");
			System.out.println("-----------------------------------------------");

			System.out.println("1) Update Employee Name");
			System.out.println("2) Update Employee SSN");
			System.out.println("3) Update Employee Contact Number");
			System.out.println("4) Update Employee Address");
			System.out.println("5) Update Employee Job Title");
			System.out.println("6) Update Manager's Team");
			System.out.println("7) Exit");

			System.out.print("\nEnter your choice - ");

			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1":
				updateName(sc, emp);
				break;

			case "2":
				updateSSN(sc, emp);
				break;

			case "3":
				updateContactNo(sc, emp);
				break;

			case "4":
				updateAddress(sc, emp);
				break;

			case "5":
				updateJobTitle(sc, emp);
				break;

			case "6":
				updateTeam(sc, emp);
				break;

			case "7":
				System.out.println("\n---------------------------------------------");
				System.out.println("            EMS Update System Closed");
				System.out.println("---------------------------------------------");
				loop = false;
				break;

			default:
				System.out.println(E + "Invalid input..." + D);
				break;
			}
		}
	}

	public static void updateName(Scanner sc, Employee emp) {

		System.out.print("\nEnter updated Employee Name - ");
		String name = sc.nextLine().trim();

		emp.setName(name);
		System.out.println(S + "Employee Name Updated" + D);

		String email = name.toLowerCase().replaceAll("\\s", "");
		emp.setEmail(email + "@colla.com");

		((Manager) empDb.get(emp.getReportTo())).getTeam().replace(emp.getEmpId(), emp);

		System.out.println(S + "Employee email Updated" + D);

		System.out.println();
	}

	public static void updateSSN(Scanner sc, Employee emp) {

		while (true) {
			System.out.print("\nEnter updated Employee SSN - ");
			int ssn = 0;
			try {
				ssn = Integer.parseInt(sc.nextLine().trim());
			} catch (Exception e) {
				System.out.println(E + "Invalid input. SSN must be a positive integer with 9 digits. Try again." + D);
				continue;
			}
			if (ssn < 1 || String.valueOf(ssn).length() != 9) {
				System.out.println(E + "Invalid input. SSN must be a positive integer with 9 digits. Try again." + D);
				continue;
			}
			emp.setSsn(ssn);

			((Manager) empDb.get(emp.getReportTo())).getTeam().replace(emp.getEmpId(), emp);

			System.out.println(S + "Employee SSN Updated" + D);
			break;
		}

		System.out.println();
	}

	public static void updateContactNo(Scanner sc, Employee emp) {

		while (true) {
			System.out.print("\nEnter updated Employee Contact No - ");
			long contactNo = 0;
			try {
				contactNo = Long.parseLong(sc.nextLine().trim());
			} catch (Exception e) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			}
			if (contactNo < 1 || String.valueOf(contactNo).length() != 10
					|| String.valueOf(contactNo).startsWith("0")) {
				System.out.println(
						E + "Invalid input. Contact Number must be a positive integer with 10 digits. Try again." + D);
				continue;
			}
			emp.setContactNo(contactNo);

			((Manager) empDb.get(emp.getReportTo())).getTeam().replace(emp.getEmpId(), emp);

			System.out.println(S + "Employee Contact Number Updated" + D);
			break;
		}

		System.out.println();
	}

	public static void updateAddress(Scanner sc, Employee emp) {

		System.out.println("\n----------Enter updated Address Info----------");
		Address addressEdit = emp.getAddress();

		System.out.print("Enter updated Street Address - ");
		addressEdit.setStreetAddress(sc.nextLine());
		System.out.print("Enter updated City - ");
		addressEdit.setCity(sc.nextLine());
		System.out.print("Enter updated State - ");
		addressEdit.setState(sc.nextLine());
		System.out.print("Enter updated Country - ");
		addressEdit.setCountry(sc.nextLine());

		while (true) {
			int zipCode = 0;
			System.out.print("Enter updated Zip Code - ");
			try {
				zipCode = Integer.parseInt(sc.nextLine().trim());
			} catch (Exception e) {
				System.out
						.println(E + "Invalid input. Zipcode must be a positive integer with 5 digits. Try again." + D);
				continue;
			}
			if (zipCode < 1 || String.valueOf(zipCode).length() != 5) {
				System.out
						.println(E + "Invalid input. Zipcode must be a positive integer with 5 digits. Try again." + D);
				continue;
			}
			addressEdit.setZipCode(zipCode);

			((Manager) empDb.get(emp.getReportTo())).getTeam().replace(emp.getEmpId(), emp);

			System.out.println(S + "Employee Address Details Updated" + D);
			break;

		}
		System.out.println();
	}

	public static void updateJobTitle(Scanner sc, Employee emp) {

		System.out.print("\nEnter updated Employee Job Title - ");
		String jobTitle = sc.nextLine().trim();

		emp.setJobTitle(jobTitle);

		((Manager) empDb.get(emp.getReportTo())).getTeam().replace(emp.getEmpId(), emp);

		System.out.println(S + "Employee Job Title Updated" + D);

		System.out.println();

	}

	public static void updateTeam(Scanner sc, Employee emp) {
		Manager m;
		String strEmpId;
		int empId;
		Employee e;

		if (emp.isManager()) {
			m = (Manager) empDb.get(emp.getEmpId());
			System.out.println(
					S + "\nSelect Employee Id from below table to add Employee to " + m.getName() + "'s Team\n" + D);
			printEmployeeManager();
			while (true) {
				System.out.print("Enter Employee Id to add Employee to " + m.getName() + "\'s team (or Q to quit) - ");
				strEmpId = sc.nextLine().trim();
				if (strEmpId.toUpperCase().equals("Q")) {
					break;
				}
				try {
					empId = Integer.parseInt(strEmpId);
				} catch (NumberFormatException e2) {
					System.out.println(E + "Invalid input. Employee Id must be a positive integer. Try again." + D);
					continue;
				}
				if (empId < 1) {
					System.out.println(E + "Invalid input. Employee Id must be a positive integer. Try again." + D);
					continue;
				}
				if (!empDb.containsKey(empId)) {
					System.out.println(E + "Invalid input. Entered Employee Id not found. Try again." + D);
					continue;
				} else {
					if (empDb.get(empId).isManager()) {
						System.out.println("Found Manager can't be assigned under Teams...");
						break;
					} else {
						if (m.getTeam().containsKey(empId)) {
							System.out.println(
									empDb.get(empId).getName() + " already belongs to " + m.getName() + "'s Team...");
							break;
						} else {
							e = empDb.get(empId);
							Map<Integer, Employee> oldTeam = ((Manager) empDb.get(e.getReportTo())).getTeam();
							oldTeam.remove(e.getEmpId());
							Map<Integer, Employee> newTeam = m.getTeam();
							newTeam.put(empId, e);
							e.setReportTo(emp.getEmpId());
							if (empMap.containsKey(empId))
								empMap.replace(empId, emp.getEmpId());
							System.out
									.println(S + "\n" + e.getName() + " added to " + m.getName() + "\'s team...\n" + D);
							break;
						}
					}
				}
			}
		} else {
			System.out.println("\nFound Employee is not a Manager...\n");
		}
	}

	public static void delete(Scanner sc) {

		boolean loop = true;
		while (loop) {

			System.out.println("-----------------------------------------------");
			System.out.println("             Delete Employee by Id");
			System.out.println("-----------------------------------------------");

			System.out.println("1) Delete Employee by Id");
			// System.out.println("2) Delete Employee by Name");
			System.out.println("2) Exit");

			System.out.print("\nEnter your choice - ");

			String choice = sc.nextLine().trim();

			switch (choice) {
			case "1":
				deleteById(sc);
				break;

//			case "2":
//				deleteByName(sc);
//				break;

			case "2":
				System.out.println("\n---------------------------------------------");
				System.out.println("            EMS Delete System Closed");
				System.out.println("---------------------------------------------");
				loop = false;
				break;

			default:
				System.out.println(E + "Invalid input..." + D);
				break;
			}
		}
	}

	public static void deleteById(Scanner sc) {

		Employee emp = searchByEmpId(sc);

		if (emp != null) {
			int empId = emp.getEmpId();
			if (emp.isManager()) {
				empDb.remove(empId);
				((Manager) emp).getTeam().clear();
				for (Entry<Integer, Integer> i : empMap.entrySet()) {
					if (i.getValue().equals(empId)) {
						i.setValue(0);
						empDb.get(i.getKey()).setReportTo(0);
					}
				}

				System.out.println(S + "Manager with entered Id deleted..." + D);
			} else {
				empDb.remove(empId);
				empMap.remove(empId);
				((Manager) empDb.get(emp.getReportTo())).getTeam().remove(empId);

				System.out.println(S + "Employee with entered Id deleted..." + D);
			}
		}
		System.out.println();
	}

	public static void deleteByName(Scanner sc) {

		boolean nameFound = false;
		System.out.print("\nEnter Name to delete for - ");
		String name = sc.nextLine().trim();
		for (Employee e : empDb.values()) {
			if (e.getName().equals(name)) {
				empDb.remove(e.getEmpId());
				empMap.remove(e.getEmpId());
				System.out.println(S + "Employee with entered Name deleted..." + D);
				nameFound = true;
				break;
			}
		}
		if (!nameFound) {
			System.out.println(E + "Employee with entered Name doesn't exists..." + D);
		}
		System.out.println();
	}
}