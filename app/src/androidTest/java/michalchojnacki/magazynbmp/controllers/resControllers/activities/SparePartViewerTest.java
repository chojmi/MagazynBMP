package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
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

    @Rule
    public ActivityTestRule<SparePartViewer> mSparePartViewer = new ActivityTestRule<>(SparePartViewer.class, false, false);

    @Before
    public void init() {

        SparePart sparePart = new SparePart.Builder()
                .number("number")
                .description("description")
                .type("type")
                .producer("producer")
                .location("location")
                .build();

        Intent intent = new Intent().putExtra(SparePartViewer.SPARE_PART, sparePart);
        mSparePartViewer.launchActivity(intent);
    }

    @Test
    public void numberProperlyRead() {
        Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(withText("number")));
    }

    @Test
    public void descriptionProperlyRead() {
        Espresso.onView(withId(R.id.SparePartDescriptionText)).check(matches(withText("description")));
    }

    @Test
    public void typeProperlyRead() {
        Espresso.onView(withId(R.id.SparePartTypeText)).check(matches(withText("type")));
    }

    @Test
    public void locationProperlyRead() {
        Espresso.onView(withId(R.id.SparePartLocationText)).check(matches(withText("location")));
    }

    @Test
    public void producerProperlyRead() {
        Espresso.onView(withId(R.id.SparePartProducerText)).check(matches(withText("producer")));
    }
}