package project;
	
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		StudentFunc student = new StudentFunc();
		
		Student student1 = new Student(1, "Juraj", "Pistej", LocalDate.of(2004, 1, 8), new ArrayList<>(), StudyGroup.KYBERBEZPECNOST);
		while (run) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Přidat nového studenta");
            System.out.println("2. Zobrazit všechny studenty");
            System.out.println("0. Konec");

            System.out.print("Vyber akci: ");
            String volba = scanner.nextLine();

            switch (volba) {
                case "1":
                	student.addStudent();
                    break;
                case "2":
                	System.out.println(student1);
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
