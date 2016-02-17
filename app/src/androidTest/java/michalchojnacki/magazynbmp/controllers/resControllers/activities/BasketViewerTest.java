package michalchojnacki.magazynbmp.controllers.resControllers.activities;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.model.SparePart;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class BasketViewerTest {

    @Rule public ActivityTestRule<BasketViewer> mBasketViewer =
            new ActivityTestRule(BasketViewer.class, false, false);
    ArrayList<SparePart> mSpareParts = new ArrayList<>();
    private BasketController mBasketController = new BasketController();
    private String numberText = "number";
    private int size = 3;

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher,
                                           final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText(
                        "with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition)
                        .equals(view);
            }
        };
    }

    @Test(expected = PerformException.class)
    public void properQuantityOfItems() {
        initTest();
        Espresso.onView(withId(R.id.SparePartsTrayRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(size + 1, ViewActions.click()));
    }

    private void initTest() {

        for (int i = 0; i < size; i++) {
            SparePart sparePart = new SparePart.Builder().number(numberText + i).build();
            mSpareParts.add(sparePart);
            mBasketController.addToBasket(sparePart, i);
        }

        Intent intent = new Intent().putExtra(BasketViewer.BASKET_CONTROLLER, mBasketController);
        mBasketViewer.launchActivity(intent);
    }

    @Test
    public void ShortClickTest() {
        initTest();
        for (int i = 0; i < size; i++) {
            Espresso.onView(withId(R.id.SparePartsTrayRecyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click()));
            Espresso.onView(withId(R.id.SparePartNumberText))
                    .check(matches(withText(numberText + i)));
            Espresso.pressBack();
        }
    }

    @Test
    public void LongClickTest() {
        initTest();
        for (int i = 0; i < size; i++) {
            Espresso.onView(withId(R.id.SparePartsTrayRecyclerView))
                    .perform(
                            RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.longClick()));
            Espresso.onView(withId(R.id.changeBasketQuantity))
                    .check(matches(withText(String.valueOf(i))));
            Espresso.onView(withText(R.string.CancelLabel)).perform(ViewActions.click());
        }
    }

    @Test
    public void changeOptionTest() {
        initTest();
        for (int i = 0; i < size; i++) {
            Espresso.onView(withId(R.id.SparePartsTrayRecyclerView))
                    .perform(
                            RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.longClick()));
            Espresso.onView(withId(R.id.changeBasketQuantity))
                    .perform(ViewActions.replaceText(String.valueOf(i + 10)));
            Espresso.onView(withText(R.string.ChangeLabel)).perform(ViewActions.click());
        }
        for (int i = 0; i < size; i++) {
            Espresso.onView(withId(R.id.SparePartsTrayRecyclerView))
                    .perform(
                            RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.longClick()));
            Espresso.onView(withId(R.id.changeBasketQuantity))
                    .check(matches(withText(String.valueOf(10 + i))));
            Espresso.onView(withText(R.string.CancelLabel)).perform(ViewActions.click());
        }
    }

    @Test
    public void clearButtonWorks() {

    }

    @After
    public void clearData() {
        mSpareParts.clear();
        mBasketController.clear();
    }
}