package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

;

@RunWith(AndroidJUnit4.class)
public class SparePartsDbControllerTest {

    private SparePartsDbController sparePartsDbController;

    @Before
    public void initSparePartsDbController() {
        sparePartsDbController = new SparePartsDbController(InstrumentationRegistry.getTargetContext());
        SparePart sparePart = new SparePart.Builder().number("YA2020").build();
        sparePartsDbController.saveSparePart(sparePart);
    }

    @Test
    public void countOfSparePartsAfterDeletingAll() {
        sparePartsDbController.deleteAllSpareParts();
        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(0));
    }

    @Test
    public void countOfSparePartsAfterSaving() {
        SparePart sparePart = new SparePart.Builder().number("YA2010").build();

        sparePartsDbController.saveSparePart(sparePart);

        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(2));
    }

    @Test
    public void testFindSparePartByString() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("YA2020");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }

    @Test
    public void findSparePartBySparePartWhenIsPresent() {
        SparePart sparePart = new SparePart.Builder().number("YA2020").build();

        SparePart[] spareParts = sparePartsDbController.findSparePart(sparePart);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }

    @Test
    public void findSparePartBySparePartWhenIsNotPresent() {
        SparePart sparePart = new SparePart.Builder().number("YA2021").build();

        SparePart[] spareParts = sparePartsDbController.findSparePart(sparePart);

        assertThat(spareParts, equalTo(null));
    }

    @Test
    public void findSparePartWhenNull() {
        SparePart[] spareParts = sparePartsDbController.findSparePart((String) null);

        assertThat(spareParts, equalTo(null));
    }

    @After
    public void deleteAllSpareParts() {
        sparePartsDbController.deleteAllSpareParts();
    }

}