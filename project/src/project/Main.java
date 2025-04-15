package project;
	
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		StudentFunc student = new StudentFunc();
		
		student.addSampleStudents();
		while (run) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Přidat nového studenta");
            System.out.println("2. Přidat známku studentovi");
            System.out.println("3. Propustit studenta");
            System.out.println("4. Zobrazit studenta dle ID");
            System.out.println("5. Zobrazit všechny studenty");
            System.out.println("0. Konec");

            System.out.print("Vyber akci: ");
            String volba = scanner.nextLine();

            switch (volba) {
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
