package project;

import java.util.HashMap;
import java.util.Map;

public class MorseSkill implements Skill {

    @Override
    public void applySkill(Student student) {
        System.out.println("Morseova abeceda jména a příjmení:");
        String text = student.getName() + " " + student.getSurname();
        System.out.println(toMorse(text));
    }

    private static final Map<Character, String> morseCode = new HashMap<>();

    static {
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
        morseCode.put(' ', " ");
    }

    private String removeDiacritics(String text) {
        return text.toUpperCase()
                   .replace("Á", "A").replace("Č", "C").replace("Ď", "D")
                   .replace("É", "E").replace("Ě", "E").replace("Í", "I")
                   .replace("Ĺ", "L").replace("Ľ", "L").replace("Ň", "N")
                   .replace("Ó", "O").replace("Ô", "O").replace("Ř", "R")
                   .replace("Ŕ", "R").replace("Š", "S").replace("Ť", "T")
                   .replace("Ú", "U").replace("Ů", "U").replace("Ý", "Y").replace("Ž", "Z");
    }

    private String toMorse(String text) {
        StringBuilder sb = new StringBuilder();
        text = removeDiacritics(text);
        for (char c : text.toCharArray()) {
            sb.append(morseCode.getOrDefault(c, "?")).append(" ");
        }
        return sb.toString().trim();
    }
}
