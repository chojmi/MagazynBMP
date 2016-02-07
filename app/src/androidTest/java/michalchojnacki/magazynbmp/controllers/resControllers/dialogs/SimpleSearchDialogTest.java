package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;
import michalchojnacki.magazynbmp.model.SparePart;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SimpleSearchDialogTest {

    @Rule
    public ActivityTestRule<StartActivity> mStartActivity = new ActivityTestRule(StartActivity.class);

    private SimpleSearchDialog simpleSearchDialog;
    private SparePartsDbController sparePartsDbController;

    @Before
    public void init() {
        sparePartsDbController = new SparePartsDbController(mStartActivity.getActivity());
        simpleSearchDialog = new SimpleSearchDialog();
        simpleSearchDialog.setSparePartsDbController(sparePartsDbController);
        simpleSearchDialog.show(mStartActivity.getActivity().getSupportFragmentManager(), "fragment_simple_search_for_part");
    }

    @Test
    public void disappearsWhenCancelled() {
        Espresso.onView(withText(R.string.SearchLabel)).check(matches(isDisplayed()));

        Espresso.onView(withText(R.string.CancelLabel))
                .perform(ViewActions.click());

        Espresso.onView(withText(R.string.SearchLabel)).check(doesNotExist());
    }

    @Test
    public void clickOkWhenEmptyDb() {

        Espresso.onView(withText(R.string.OkLabel))
                .perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void sparePartViewerShowsWhenFoundOneItem() {
        SparePart sparePart = new SparePart.Builder()
                .number("number")
                .build();

        sparePartsDbController.saveSparePart(sparePart);

        Espresso.onView(withId(R.id.DialogSimpleSearchText)).perform(ViewActions.replaceText("number"));

        Espresso.onView(withText(R.string.OkLabel))
                .perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void sparePartViewerShowsWhenFoundThreeItems() {
        int size = 3;

        for (int i = 0; i < size; i++) {
            sparePartsDbController.saveSparePart(new SparePart.Builder()
                    .number("number " + i)
                    .build());
        }

        Espresso.onView(withId(R.id.DialogSimpleSearchText)).perform(ViewActions.replaceText("number"));

        Espresso.onView(withText(R.string.OkLabel))
                .perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartsRecyclerView)).check(matches(isDisplayed()));
    }

    @After
    public void clearData() {
        sparePartsDbController.deleteAllSpareParts();
    }
}