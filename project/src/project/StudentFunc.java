package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentFunc {
	private int nextId = 1;
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
        
        Skill skill = null;
        if (group == StudyGroup.TELEKOMUNIKACE) {
            skill = new MorseSkill();
        } else if (group == StudyGroup.KYBERBEZPECNOST) {
            skill = new HashSkill();
        }

        Student newStudent = new Student(nextId++, name, surname, birthday, new ArrayList<>(), group, skill);
        students.add(newStudent);

        System.out.println("Student úspěšně přidán.");
    }
    
    public void addSampleStudents() {
        students.add(new Student(nextId++, "Jan", "Novák", LocalDate.of(2003, 5, 12), new ArrayList<>(List.of(1, 2, 3)), StudyGroup.KYBERBEZPECNOST, new HashSkill()));
        students.add(new Student(nextId++, "Petra", "Svobodová", LocalDate.of(2004, 8, 22), new ArrayList<>(List.of(2, 2, 1, 3)), StudyGroup.KYBERBEZPECNOST, new HashSkill()));
        students.add(new Student(nextId++, "Lukáš", "Dvořák", LocalDate.of(2002, 11, 3), new ArrayList<>(List.of(3, 4)), StudyGroup.TELEKOMUNIKACE, new MorseSkill()));
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
	
	public void addGradeToStudent() {
	    System.out.print("Zadej ID studenta: ");
	    int id;
	    try {
	        id = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatné ID.");
	        return;
	    }

	    Student student = null;
	    for (Student s : students) {
	        if (s.getId() == id) {
	            student = s;
	            break;
	        }
	    }

	    if (student == null) {
	        System.out.println("Student s daným ID neexistuje.");
	        return;
	    }

	    System.out.print("Zadej známku (1 - 5): ");
	    int grade;
	    try {
	        grade = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatná známka.");
	        return;
	    }

	    student.setGrade(grade);
	    System.out.println("Známka byla úspěšně přidána.");
	}
	
	public void removeStudent() {
	    System.out.print("Zadej ID studenta, kterého chceš odstranit: ");
	    int id;
	    try {
	        id = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatné ID.");
	        return;
	    }

	    Iterator<Student> iterator = students.iterator();
	    while (iterator.hasNext()) {
	        Student student = iterator.next();
	        if (student.getId() == id) {
	            iterator.remove();
	            System.out.println("Student byl úspěšně propuštěn.");
	            return;
	        }
	    }
	}
	
	public void printStudentById() {
	    System.out.print("Zadej ID studenta: ");
	    int id;
	    try {
	        id = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatné ID.");
	        return;
	    }

	    for (Student s : students) {
	        if (s.getId() == id) {
	        	System.out.println(s);
	            return;
	        }
	    }

	    System.out.println("Student s ID " + id + " nebyl nalezen.");
	}
	
	public void activateSkillById() {
	    System.out.print("Zadej ID studenta: ");
	    int id;
	    try {
	        id = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatné ID.");
	        return;
	    }

	    Student student = null;
	    for (Student s : students) {
	        if (s.getId() == id) {
	            student = s;
	            break;
	        }
	    }

	    if (student == null) {
	        System.out.println("Student s daným ID neexistuje.");
	        return;
	    }

	    if (student.getSkill() != null) {
	        student.getSkill().execute(student);
	    } else {
	        System.out.println("Student nemá přiřazenou žádnou dovednost.");
	    }
	}
	
	public void printStudentsByGroup() {
	    Map<StudyGroup, List<Student>> studyGroup = new HashMap<>();

	    for (Student student : students) {
	        StudyGroup group = student.getGroup();
	        if (!studyGroup.containsKey(group)) {
	        	studyGroup.put(group, new ArrayList<>());
	        }
	        studyGroup.get(group).add(student);
	    }

	    for (StudyGroup group : studyGroup.keySet()) {
	        System.out.println("\nSkupina: " + group);

	        List<Student> studentsInGroup = studyGroup.get(group);
	        studentsInGroup.sort(Comparator.comparing(Student::getSurname, String.CASE_INSENSITIVE_ORDER));

	        for (Student student : studentsInGroup) {
	            System.out.println(student);
	        }
	    }
	}

    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
