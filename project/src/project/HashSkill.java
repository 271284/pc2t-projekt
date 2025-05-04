package project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSkill implements Skill {

    @Override
    public void applySkill(Student student) {
        System.out.println("Hash jména a příjmení:");
        String text = student.getName() + " " + student.getSurname();
        System.out.println(toHash(text));
    }

    private String toHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Chyba při hashování.";
        }
    }
}