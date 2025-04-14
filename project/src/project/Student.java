package project;

import java.time.LocalDate;
import java.util.List;

public class Student {
	 private int id;
	 private String name;
	 private String surname;
	 private LocalDate birthday;
	 private List<Integer> grades;
	 private StudyGroup group;
	 

	public Student(int id, String name, String surname, LocalDate birthday, List<Integer> grades, StudyGroup group) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.grades = grades;
		this.group = group;
	}

	public List<Integer> getGrade() {
		return grades;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setGrade(int grade) {
		if (grade < 1 || grade > 5) {
			System.out.println("Známka od 1 do 5");
		}
		grades.add(grade);
	}
	
	public double averageGrade() {
		return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
	}

	@Override
    public String toString() {
        return "ID: " + id +
               ", " + name + " " + surname +
               ", Obor: " + group +
               ", Datum narození: " + birthday.getDayOfMonth() +"." + birthday.getMonthValue() + "." + birthday.getYear() +
               ", Známky: " + grades +
               ", Průměr: " + String.format("%.2f", averageGrade());
    }
	 
}
