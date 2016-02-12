package michalchojnacki.magazynbmp.controllers.dbControllers;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SqlCheckEntryBuilderTest {

    @Test
    public void polishLettersToEnglish() {
        assertThat(SqlCheckEntryBuilder.getWordsToCheck("łożysko"), equalTo(new String[]{"lozysko", "łożysko", "łóżyśkó"}));
    }
}