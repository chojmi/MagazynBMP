package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import junit.framework.AssertionFailedError;

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
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
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
        clearCheckboxes();
    }

    private void clearCheckboxes() {
        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isNotChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox))
                    .check(matches(isNotChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.DialogSearchTypeCheckbox)).check(matches(isNotChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchTypeCheckbox)).perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.DialogSearchProducerCheckbox))
                    .check(matches(isNotChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchProducerCheckbox)).perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.DialogSearchSupplierCheckbox))
                    .check(matches(isNotChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchSupplierCheckbox)).perform(ViewActions.click());
        }
    }

    @Test
    public void searchByOnlyNumberWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartNumber))
                .perform(ViewActions.replaceText(spareParts.get(0).getNumber()));

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
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
    public void searchByOnlyTypeWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.DialogSearchTypeText))
                .perform(ViewActions.replaceText(spareParts.get(0).getType()));

        try {
            Espresso.onView(withId(R.id.DialogSearchTypeCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchTypeCheckbox)).perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlyProducerWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.DialogSearchProducerText))
                .perform(ViewActions.replaceText(spareParts.get(0).getProducer()));

        try {
            Espresso.onView(withId(R.id.DialogSearchProducerCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchProducerCheckbox)).perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlySupplierWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.DialogSearchSupplierText))
                .perform(ViewActions.replaceText(spareParts.get(0).getSupplier()));

        try {
            Espresso.onView(withId(R.id.DialogSearchSupplierCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.DialogSearchSupplierCheckbox)).perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlyDescriptionWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartDescription))
                .perform(ViewActions.replaceText(spareParts.get(0).getDescription()));

        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlyDescriptionWhenSparePartsFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartDescription))
                .perform(ViewActions.replaceText(descText));

        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartsRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void searchWithTwoConditionsWhenSthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartDescription))
                .perform(ViewActions.replaceText(spareParts.get(0).getDescription()));

        Espresso.onView(withId(R.id.searchingPartNumber))
                .perform(ViewActions.replaceText(spareParts.get(0).getNumber()));

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(doesNotExist());
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(isDisplayed()));
    }

    @Test
    public void searchWithTwoConditionsWhenOnlyOneMatch() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartDescription))
                .perform(ViewActions.replaceText(spareParts.get(0).getDescription()));

        Espresso.onView(withId(R.id.searchingPartNumber)).perform(ViewActions.replaceText("xx"));

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());
        }

        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlyDescWhenNthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartDescription))
                .perform(ViewActions.replaceText("xx"));

        try {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartDescriptionCheckbox))
                    .perform(ViewActions.click());
        }

        Espresso.onView(withText(R.string.OkLabel)).perform(ViewActions.click());

        Espresso.onView(withText(R.string.NoSparePartFoundLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void searchByOnlyNumberWhenNthFound() {
        spareParts = getSparePartsWithDiffValues(sparePartsDbController);
        Espresso.onView(withId(R.id.searchingPartNumber)).perform(ViewActions.replaceText("xx"));

        try {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).check(matches(isChecked()));
        } catch (AssertionFailedError e) {
            Espresso.onView(withId(R.id.searchingPartNumberCheckbox)).perform(ViewActions.click());
        }

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