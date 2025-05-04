package project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:students.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String createTable = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "surname TEXT NOT NULL," +
                    "birthday TEXT NOT NULL," +
                    "grades TEXT," +
                    "groupName TEXT NOT NULL," +
                    "skillType TEXT" +
                    ")";
            stmt.execute(createTable);

        } catch (SQLException e) {
            System.out.println("Chyba při inicializaci databáze: " + e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "REPLACE INTO students (id, name, surname, birthday, grades, groupName, skillType) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            for (Student s : students) {
                pstmt.setInt(1, s.getId());
                pstmt.setString(2, s.getName());
                pstmt.setString(3, s.getSurname());
                pstmt.setString(4, s.getBirthday().toString());
                pstmt.setString(5, s.getGrades().toString().replaceAll("[\\[\\]]", ""));
                pstmt.setString(6, s.getGroup().name());
                pstmt.setString(7, s.getSkill() != null ? s.getSkill().getClass().getSimpleName() : null);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Studenti byli uloženi do databáze.");

        } catch (SQLException e) {
            System.out.println("Chyba při ukládání studentů do DB: " + e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM students";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String bday = rs.getString("birthday");
                LocalDate birthday = LocalDate.parse(bday);
                String groupStr = rs.getString("groupName");
                StudyGroup group = StudyGroup.valueOf(groupStr);
                String gradesStr = rs.getString("grades");
                List<Integer> grades = new ArrayList<>();
                
                if (gradesStr != null && !gradesStr.isBlank()) {
                    grades = Arrays.stream(gradesStr.split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                }

                Skill skill = (group == StudyGroup.KYBERBEZPECNOST) ? new HashSkill() : new MorseSkill();
                Student student = new Student(id, name, surname, birthday, grades, group, skill);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Chyba při načítání studentů z DB: " + e.getMessage());
        }

        return students;
    }
    
    public static void deleteStudent(int id) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Chyba při odstraňování studenta z databáze: " + e.getMessage());
        }
    }
}