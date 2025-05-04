package project;
	
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		StudentFunc student = new StudentFunc();
		student.loadStudentsFromDatabase();
		DatabaseManager.initializeDatabase();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		    DatabaseManager.saveStudents(student.getStudents());
		}));
		while (run) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Přidat nového studenta");
            System.out.println("2. Přidat známku studentovi");
            System.out.println("3. Propustit studenta");
            System.out.println("4. Zobrazit studenta dle ID");
            System.out.println("5. Spustit dovednost studenta dle ID");
            System.out.println("6. Zobrazit studenty dle skupiny");
            System.out.println("7. Zobrazit obecný studijní průmer v oborech");
            System.out.println("8. Vypísat celkový počet studentů v jednotlivých skupinách");
            System.out.println("9. Uložit studenta do souboru dle ID");
            System.out.println("10. Načíst studenta ze souboru dle ID");
            System.out.println("11. Zobrazit všechny studenty");
            System.out.println("0. Konec");

            System.out.print("Vyber akci: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                	student.addStudent();
                    break;
                case "2":
                	student.addGradeToStudent();
                	break;
                case "3":
                	student.removeStudent();
                	break;
                case "4":
                	student.printStudentById();
                	break;
                case "5":
                	student.activateSkillById();
                    break;
                case "6":
                	student.printStudentsByGroup();
                    break;
                case "7":
                	student.printAverageGrade();
                    break;
                case "8":
                	student.numberOfStudentsInGroup();
                    break;
                case "9":
                	student.saveStudentToFile();
                    break;
                case "10":
                	try {
                		student.loadStudentFromFile();
                	} catch (Exception e) {
                		System.out.println("Studenta nebylo možné načíst.");
                	}
                    break;
                case "11":
                	student.printAllStudents();
                    break;
                case "0":
                    run = false;
                    break;
                default:
                    System.out.println("Neplatná volba!");
            }
        }

	}

}
