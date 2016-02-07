package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
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
public class SparePartsViewerTest {

    @Rule
    public ActivityTestRule<SparePartsViewer> mSparePartsViewer = new ActivityTestRule(SparePartsViewer.class, false, false);
    private int size = 10;

    @Test
    public void activityStartedWithoutExtra() {
        mSparePartsViewer.launchActivity(new Intent());
    }

    @Test
    public void sparePartViewerOpensProperly() {
        initSpareParts();

        for (int i = 0; i < size; i++) {
            Espresso.onView(withId(R.id.SparePartsRecyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click()));
            Espresso.onView(withId(R.id.SparePartNumberText)).check(matches(withText("number " + i)));
            Espresso.onView(withId(R.id.SparePartDescriptionText)).check(matches(withText("description " + i)));
            Espresso.onView(withId(R.id.SparePartTypeText)).check(matches(withText("type " + i)));
            Espresso.onView(withId(R.id.SparePartLocationText)).check(matches(withText("location " + i)));
            Espresso.onView(withId(R.id.SparePartProducerText)).check(matches(withText("producer " + i)));
            Espresso.pressBack();
        }
    }

    private void initSpareParts() {

        SparePart[] spareParts = new SparePart[size];

        for (int i = 0; i < size; i++) {
            spareParts[i] = new SparePart.Builder()
                    .number("number " + i)
                    .description("description " + i)
                    .type("type " + i)
                    .producer("producer " + i)
                    .location("location " + i)
                    .build();
        }

        Intent intent = new Intent().putExtra(SparePartsViewer.SPARE_PARTS, spareParts);
        mSparePartsViewer.launchActivity(intent);
    }
}