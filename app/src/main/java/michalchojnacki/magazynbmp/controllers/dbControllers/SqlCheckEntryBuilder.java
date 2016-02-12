package michalchojnacki.magazynbmp.controllers.dbControllers;

import java.util.LinkedList;
import java.util.List;

public class SqlCheckEntryBuilder {

    public static String[] getWordsToCheck(String searchedText) {
        List<String> wordsToCheck = new LinkedList<>();
        wordsToCheck.add(searchedText);
        wordsToCheck.add("czujnik");

        return wordsToCheck.toArray(new String[wordsToCheck.size()]);
    }
}
