package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentFunc {
	private Scanner scanner = new Scanner(System.in);
	private List<Student> students = new ArrayList<>();

    public void addStudent() {
    	System.out.print("Zadej jméno: ");
        String name = scanner.nextLine();

        System.out.print("Zadej příjmení: ");
        String surname = scanner.nextLine();

        System.out.print("Zadej datum narození (DD.MM.YYYY): ");
        String dateOfBirth = scanner.nextLine();

        LocalDate birthday = parseDate(dateOfBirth);
        if (birthday == null) {
            System.out.println("Neplatný datum. Operace zrušena.");
            return;
        }

        StudyGroup group = selectStudyGroup();
        if (group == null) {
            System.out.println("Neplatná volba skupiny.");
            return;
        }

        int newId = students.size() + 1;
        Student newStudent = new Student(newId, name, surname, birthday, new ArrayList<>(), group);
        students.add(newStudent);

        System.out.println("Student úspěšně přidán:");
    }

    private LocalDate parseDate(String dateOfBirth) {
    	String[] dateParts = dateOfBirth.split("\\.");
        if (dateParts.length != 3) {
            return null;
        }

        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2025) {
                return null;
            }

            return LocalDate.of(year, month, day);
        } catch (NumberFormatException e) {
            return null;
        }
	}

	private StudyGroup selectStudyGroup() {
    	System.out.println("Vyber studijní skupinu:");
        for (int i = 0; i < StudyGroup.values().length; i++) {
            System.out.println((i + 1) + ". " + StudyGroup.values()[i]);
        }

        System.out.print("Zadej číslo skupiny: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= StudyGroup.values().length) {
                return StudyGroup.values()[choice - 1];
            }
        } catch (NumberFormatException ignored) {}

        return null;
	}

    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
