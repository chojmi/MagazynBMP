package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class SparePartsDbControllerTest {

    private SparePartsDbController sparePartsDbController;
    private String validTestNumber1 = "YA2020";
    private String validTestNumber2 = "YA2010";
    private String notValidTestNumber = "YA2021";

    @Before
    public void initSparePartsDbController() {
        sparePartsDbController =
                new SparePartsDbController(InstrumentationRegistry.getTargetContext());
        deleteAllSpareParts();
        SparePart sparePart = new SparePart.Builder().number(validTestNumber1)
                .description("desc")
                .producer("producer")
                .type("type")
                .location("00-00-00")
                .supplier("00-00-00")
                .build();
        sparePartsDbController.saveSparePart(sparePart);
    }

    @After
    public void deleteAllSpareParts() {
        sparePartsDbController.deleteAllSpareParts();
    }

    @Test
    public void countOfSparePartsAfterDeletingAll() {
        sparePartsDbController.deleteAllSpareParts();
        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(0));
    }

    @Test
    public void countOfSparePartsAfterSaving() {
        SparePart sparePart = new SparePart.Builder().number(validTestNumber2).build();

        sparePartsDbController.saveSparePart(sparePart);

        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(2));
    }

    @Test
    public void findSparePartBySparePartWhenIsPresent() {
        SparePart sparePart = new SparePart.Builder().number(validTestNumber1).build();

        SparePart[] spareParts = sparePartsDbController.findSparePart(sparePart);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo(validTestNumber1));
    }

    @Test
    public void findSparePartBySparePartWhenIsNotPresent() {
        SparePart sparePart = new SparePart.Builder().number(notValidTestNumber).build();

        SparePart[] spareParts = sparePartsDbController.findSparePart(sparePart);

        assertThat(spareParts, equalTo(null));
    }

    @Test
    public void findSparePartWhenNull() {
        SparePart[] spareParts = sparePartsDbController.findSparePart((String) null);

        assertThat(spareParts, equalTo(null));
    }

}