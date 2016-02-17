package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.model.SparePart;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SparePartViewerTest {

    @Rule public ActivityTestRule<SparePartViewer> mSparePartViewer =
            new ActivityTestRule(SparePartViewer.class, false, false);
    private String numberText = "number";
    private String locationText = "location";
    private String descText = "desc";
    private String producerText = "producer";
    private String typeText = "type";
    private String supplierText = "supplier";

    @Test
    public void activityLaunchedWithoutExtra() {
        mSparePartViewer.launchActivity(new Intent());
    }

    @Test
    public void numberProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(withText(numberText)));
    }

    private void initSparePart() {

        SparePart sparePart = new SparePart.Builder().number(numberText)
                .description(descText)
                .type(typeText)
                .producer(producerText)
                .location(locationText)
                .supplier(supplierText)
                .build();

        Intent intent = new Intent().putExtra(SparePartViewer.SPARE_PART, sparePart);
        mSparePartViewer.launchActivity(intent);
    }

    @Test
    public void descriptionProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartDescriptionText)).check(matches(withText(descText)));
    }

    @Test
    public void typeProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartTypeText)).check(matches(withText(typeText)));
    }

    @Test
    public void locationProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartLocationText)).check(matches(withText(locationText)));
    }

    @Test
    public void supplierProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartSupplierText)).check(matches(withText(supplierText)));
    }

    @Test
    public void producerProperlyRead() {
        initSparePart();
        Espresso.onView(withId(R.id.SparePartProducerText)).check(matches(withText(producerText)));
    }
}