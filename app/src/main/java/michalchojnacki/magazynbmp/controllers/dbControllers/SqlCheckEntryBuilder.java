package michalchojnacki.magazynbmp.controllers.dbControllers;

import java.util.HashSet;
import java.util.Set;

public class SqlCheckEntryBuilder {

    public static String[] getWordsToCheck(String searchedText) {
        Set<String> wordsToCheck = new HashSet<>();
        wordsToCheck.add(searchedText);
        wordsToCheck.add(searchedText
                .replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z"));

        wordsToCheck.add(searchedText
                .replace("a", "ą")
                .replace("c", "ć")
                .replace("e", "ę")
                .replace("l", "ł")
                .replace("n", "ń")
                .replace("o", "ó")
                .replace("s", "ś")
                .replace("z", "ź"));

        wordsToCheck.add(searchedText
                .replace("a", "ą")
                .replace("c", "ć")
                .replace("e", "ę")
                .replace("l", "ł")
                .replace("n", "ń")
                .replace("o", "ó")
                .replace("s", "ś")
                .replace("z", "ż"));

        return wordsToCheck.toArray(new String[wordsToCheck.size()]);
    }
}
