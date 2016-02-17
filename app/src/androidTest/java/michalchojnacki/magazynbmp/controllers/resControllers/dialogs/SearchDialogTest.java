package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;
import michalchojnacki.magazynbmp.model.SparePart;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SearchDialogTest {

    @Rule public ActivityTestRule<StartActivity> mStartActivity =
            new ActivityTestRule(StartActivity.class);
    private ArrayList<SparePart> spareParts = new ArrayList<>();

    private String numberText = "number";
    private String locationText = "location";
    private String descText = "desc";
    private String producerText = "producer";
    private String typeText = "type";
    private String supplierText = "supplier";
    private int size = 3;


    private SearchDialog searchDialog;
    private SparePartsDbController sparePartsDbController;

    @Before
    public void init() {
        sparePartsDbController = new SparePartsDbController(mStartActivity.getActivity());
        searchDialog = new SearchDialog();
        searchDialog.setSparePartsDbController(sparePartsDbController);
        searchDialog.show(mStartActivity.getActivity().getSupportFragmentManager(),
                          "fragment_search_for_part");


    }

    @Test
    public void searchByOnlyNumberWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartNumber))
                .perform(ViewActions.replaceText(spareParts.get(0).getNumber()));

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).check(matches(isChecked()));
        } catch (NoMatchingViewException e) {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    private ArrayList<SparePart> getSparePartsWithDiffValues(
            SparePartsDbController sparePartsDbController) {
        ArrayList<SparePart> spareParts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            SparePart sparePart = new SparePart.Builder().number(numberText + i)
                    .description(descText + i)
                    .type(typeText + i)
                    .producer(producerText + i)
                    .location(locationText + i)
                    .supplier(supplierText + i)
                    .build();
            spareParts.add(sparePart);
            sparePartsDbController.saveSparePart(sparePart, false);
        }
        return spareParts;
    }

    @Test
    public void searchByOnlyNumberWhenNothFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartNumber)).perform(ViewActions.replaceText("xx"));

        Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void disappearsWhenCancelled() {
        Espresso.onView(withText(R.string.SearchLabel)).check(matches(isDisplayed()));

        Espresso.onView(withText(R.string.CancelLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.SearchLabel)).check(doesNotExist());
    }

    @Test
    public void clickOkWhenEmptyDb() {

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(matches(isDisplayed()));
    }

    @After
    public void clearData() {
        sparePartsDbController.deleteAllSpareParts();
        spareParts.clear();
    }
}