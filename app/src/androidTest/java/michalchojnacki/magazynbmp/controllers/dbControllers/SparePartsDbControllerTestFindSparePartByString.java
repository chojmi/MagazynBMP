package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class SparePartsDbControllerTestFindSparePartByString {

    private SparePartsDbController sparePartsDbController;

    @Before
    public void initSparePartsDbController() {
        sparePartsDbController = new SparePartsDbController(InstrumentationRegistry.getTargetContext());
        deleteAllSpareParts();
        SparePart sparePart = new SparePart.Builder()
                .number("YA2020")
                .description("desc")
                .producer("producer")
                .type("type")
                .location("00-00-00")
                .supplier("supplier")
                .build();
        sparePartsDbController.saveSparePart(sparePart);
    }

    @After
    public void deleteAllSpareParts() {
        sparePartsDbController.deleteAllSpareParts();
    }

    @Test
    public void findSparePartByStringWithNumber() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("YA2020");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }

    @Test
    public void findSparePartByStringWithSupplier() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("supplier");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getSupplier(), equalTo("supplier"));
    }

    @Test
    public void findSparePartByStringWithProducer() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("producer");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }

    @Test
    public void findSparePartByStringWithDescription() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("desc");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }

    @Test
    public void findSparePartByStringWithType() {
        SparePart[] spareParts = sparePartsDbController.findSparePart("type");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));
    }
}