package project;

import java.util.HashMap;
import java.util.Map;

public class MorseSkill implements Skill {

    @Override
    public void execute(Student student) {
        System.out.println("Morseova abeceda jména a příjmení:");
        String text = student.getName() + " " + student.getSurname();
        System.out.println(toMorse(text));
    }

    private String toMorse(String text) {
        Map<Character, String> morseCode = new HashMap<>();
        morseCode.put('A', ".-"); morseCode.put('B', "-...");
        morseCode.put('C', "-.-."); morseCode.put('D', "-..");
        morseCode.put('E', "."); morseCode.put('F', "..-.");
        morseCode.put('G', "--."); morseCode.put('H', "....");
        morseCode.put('I', ".."); morseCode.put('J', ".---");
        morseCode.put('K', "-.-"); morseCode.put('L', ".-..");
        morseCode.put('M', "--"); morseCode.put('N', "-.");
        morseCode.put('O', "---"); morseCode.put('P', ".--.");
        morseCode.put('Q', "--.-"); morseCode.put('R', ".-.");
        morseCode.put('S', "..."); morseCode.put('T', "-");
        morseCode.put('U', "..-"); morseCode.put('V', "...-");
        morseCode.put('W', ".--"); morseCode.put('X', "-..-");
        morseCode.put('Y', "-.--"); morseCode.put('Z', "--..");

        StringBuilder sb = new StringBuilder();
        text = text.toUpperCase();
        for (char c : text.toCharArray()) {
            if (morseCode.containsKey(c)) {
                sb.append(morseCode.get(c)).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
