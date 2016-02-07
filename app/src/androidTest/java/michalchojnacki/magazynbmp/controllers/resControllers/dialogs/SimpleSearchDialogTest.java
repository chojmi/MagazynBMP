package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SimpleSearchDialogTest {

    @Rule
    public ActivityTestRule<StartActivity> mStartActivity = new ActivityTestRule(StartActivity.class);

    @Test
    public void disappearsWhenCancelled() {
        SimpleSearchDialog simpleSearchDialog = new SimpleSearchDialog();
        simpleSearchDialog.setSparePartsDbController(new SparePartsDbController(mStartActivity.getActivity()));
        simpleSearchDialog.show(mStartActivity.getActivity().getSupportFragmentManager(), "fragment_simple_search_for_part");

        Espresso.onView(withText(mStartActivity.getActivity().getString(R.string.SearchLabel))).check(matches(isDisplayed()));

        Espresso.onView(withText(R.string.CancelLabel))
                .perform(ViewActions.click());

        Espresso.onView(withText(mStartActivity.getActivity().getString(R.string.SearchLabel))).check(doesNotExist());

    }

}