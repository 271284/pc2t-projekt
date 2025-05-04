package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentFunc {
	private int nextId = 1;
	private Scanner scanner = new Scanner(System.in);
	private List<Student> students = new ArrayList<>();
	
	
	public List<Student> getStudents() {
	    return students;
	}
	
    public void addStudent() {
    	System.out.print("Zadej jméno: ");
        String name = scanner.nextLine();
        if (!name.matches("[\\p{L}]+([ '-][\\p{L}]+)*")) {
            System.out.println("Neplatné jméno.\nMůže obsahovat pouze písmena, mezery, apostrof nebo spojovník.");
            return;
        }

        System.out.print("Zadej příjmení: ");
        String surname = scanner.nextLine();
        if (!surname.matches("[\\p{L}]+([ '-][\\p{L}]+)*")) {
            System.out.println("Neplatné příjmení.\nMůže obsahovat pouze písmena, mezery, apostrof nebo spojovník.");
            return;
        }

        System.out.print("Zadej datum narození (DD.MM.YYYY): ");
        String dateOfBirth = scanner.nextLine();

        LocalDate birthday = parseDate(dateOfBirth);
        if (birthday == null) {
            System.out.println("Neplatný datum.");
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
        students.add(new Student(nextId++, "Petra", "Uličná", LocalDate.of(2004, 8, 22), new ArrayList<>(List.of(2, 2, 1, 3)), StudyGroup.KYBERBEZPECNOST, new HashSkill()));
        students.add(new Student(nextId++, "Lukáš", "Petrásek", LocalDate.of(2002, 11, 3), new ArrayList<>(List.of(3, 4)), StudyGroup.TELEKOMUNIKACE, new MorseSkill()));
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
	        if (grade < 1 || grade > 5) {
	            System.out.println("Známka musí být v rozmezí 1 až 5.");
	            return;
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatná známka.");
	        return;
	    }

	    student.setGrades(grade);
	    System.out.println("Známka byla úspěšně přidána.");
	}
	
	public void removeStudent() {
	    System.out.print("Zadej ID studenta, kterého chceš propustit: ");
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
	            DatabaseManager.deleteStudent(id);
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
	        student.getSkill().applySkill(student);
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
	
	public void printAverageGrade() {
	    Map<StudyGroup, List<Student>> studyGroup = new HashMap<>();

	    for (Student student : students) {
	    	StudyGroup group = student.getGroup();
	    	if (!studyGroup.containsKey(group)) {
	        	studyGroup.put(group, new ArrayList<>());
	        }
	    	studyGroup.get(group).add(student);
	    }


	    for (StudyGroup group : StudyGroup.values()) {
	        List<Student> studentsInGroup = studyGroup.get(group);

	        if (studentsInGroup == null || studentsInGroup.isEmpty()) {
	            System.out.println(group + ": žádní studenti");
	            continue;
	        }

	        double totalSum = 0;
	        int totalCount = 0;
	        for (Student student : studentsInGroup) {
	            for (int grade : student.getGrades()) {
	                totalSum += grade;
	                totalCount++;
	            }
	        }
	        double average = totalCount > 0 ? totalSum / totalCount : 0.0;
	        System.out.printf("%s: %.2f\n", group, average);
	    }
	}
	
	public void numberOfStudentsInGroup() {
		Map<StudyGroup, Integer> groupSize = new HashMap<>();

	    for (Student student : students) {
	    	StudyGroup group = student.getGroup();
	    	if (!groupSize.containsKey(group)) {
	    		groupSize.put(group, 1);
	        } else {
	        	groupSize.put(group, groupSize.get(group) + 1);
	        }
	    }
	    	
	    for (StudyGroup group : StudyGroup.values()) {
	        int count = groupSize.getOrDefault(group, 0);
	        System.out.println(group + ": " + count);
	    }
	}
	
	public void saveStudentToFile() {
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

	    String fileName = "student" + student.getId() + ".txt";

	    try (FileWriter writer = new FileWriter(fileName)) {
	        writer.write("ID: " + student.getId() + "\n");
	        writer.write("Jméno: " + student.getName() + "\n");
	        writer.write("Příjmení: " + student.getSurname() + "\n");
	        writer.write("Datum narození: " + student.getBirthday().getDayOfMonth() + "." + student.getBirthday().getMonthValue() + "." + student.getBirthday().getYear() + "\n");
	        writer.write("Skupina: " + student.getGroup() + "\n");
	        writer.write("Známky: " + student.getGrades().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "\n");
	        writer.write("Průměr: " + String.format("%.2f", student.averageGrade()) + "\n");
	        System.out.println("Student byl úspěšně uložen do souboru.");
	    } catch (IOException e) {
	        System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
	    }
	}
	
	public void loadStudentFromFile() {
	    System.out.print("Zadej ID studenta: ");
	    int id;
	    try {
	        id = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Neplatné ID.");
	        return;
	    }

	    String fileName = "student" + id + ".txt";
	    File file = new File(fileName);

	    if (!file.exists()) {
	        System.out.println("Soubor se studentem s daným ID neexistuje.");
	        return;
	    }
	    
	    Student student = null;
	    for (Student s : students) {
	        if (s.getId() == id) {
	        	System.out.println("Student s daným ID již existuje.\nChceš ho nahradit? (A/N)");
	        	String replace = scanner.nextLine().trim().toUpperCase();
	        	
	        	if(replace.equals("A")) {
	        		Iterator<Student> iterator = students.iterator();
	        	    while (iterator.hasNext()) {
	        	        student = iterator.next();
	        	        if (student.getId() == id) {
	        	            iterator.remove();
	        	            break;
	        	        }
	        	    }
	        	} else {
	        		id = nextId++;
	        	}
	        }
	    }

	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String name = null;
	        String surname = null;
	        LocalDate birthday = null;
	        List<Integer> grades = new ArrayList<>();
	        StudyGroup group = null;

	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("Jméno: ")) {
	                name = line.substring(7).trim();
	            } else if (line.startsWith("Příjmení: ")) {
	                surname = line.substring(10).trim();
	            } else if (line.startsWith("Datum narození: ")) {
	                String[] bday = line.substring(16).split("\\.");
	                int day = Integer.parseInt(bday[0]);
	                int month = Integer.parseInt(bday[1]);
	                int year = Integer.parseInt(bday[2]);
	                birthday = LocalDate.of(year, month, day);
	            } else if (line.startsWith("Známky: ")) {
	                String[] gradeStrings = line.substring(8).split(", ");
	                for (String grade : gradeStrings) {
	                    grades.add(Integer.parseInt(grade.trim()));
	                }
	            } else if (line.startsWith("Skupina: ")) {
	                String groupName = line.substring(9).trim();
	                group = StudyGroup.valueOf(groupName);
	            }
	        }

	        if (name != null && surname != null && birthday != null && group != null) {
	            Skill skill = (group == StudyGroup.KYBERBEZPECNOST) ? new HashSkill() : new MorseSkill();
	            student = new Student(id, name, surname, birthday, grades, group, skill);
	            students.add(student);
	            System.out.println("Student úspěšně načten.");
	        } else {
	            System.out.println("Soubor neobsahuje všechna potřebná data.");
	        }
	        
	    } catch (IOException | IllegalArgumentException e) {
	        System.out.println("Chyba při načítání studenta: " + e.getMessage());
	    }
	}
	
	public void loadStudentsFromDatabase() {
	    this.students = DatabaseManager.loadStudents();

	    for (Student s : students) {
	        if (s.getId() >= nextId) {
	            nextId = s.getId() + 1;
	        }
	    }

	    System.out.println("Studenti byli načteni z databáze.");
	}

    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
