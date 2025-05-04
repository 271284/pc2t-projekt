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
	 private Skill skill;
	 

	public Student(int id, String name, String surname, LocalDate birthday, List<Integer> grades, StudyGroup group, Skill skill) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.grades = grades;
		this.group = group;
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public List<Integer> getGrades() {
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

	public StudyGroup getGroup() {
		return group;
	}

	public void setGroup(StudyGroup group) {
		this.group = group;
	}

	public void setGrades(int grade) {
		if (grade < 1 || grade > 5) {
			System.out.println("Známka od 1 do 5");
			return;
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
               ", Datum narození: " + birthday.getDayOfMonth() +"." + birthday.getMonthValue() + "." + birthday.getYear() +
               ", Průměr: " + String.format("%.2f", averageGrade());
    }
	 
}
