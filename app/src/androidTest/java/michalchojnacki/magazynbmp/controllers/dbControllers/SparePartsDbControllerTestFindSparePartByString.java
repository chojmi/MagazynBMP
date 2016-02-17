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
    private String validTestNumber1 = "YA2020";
    private String descText = "desc";
    private String producerText = "producer";
    private String typeText = "type";
    private String supplier = "supplier";

    @Before
    public void initSparePartsDbController() {
        sparePartsDbController =
                new SparePartsDbController(InstrumentationRegistry.getTargetContext());
        deleteAllSpareParts();
        SparePart sparePart = new SparePart.Builder().number(validTestNumber1)
                .description(descText)
                .producer(producerText)
                .type(typeText)
                .location("00-00-00")
                .supplier(supplier)
                .build();
        sparePartsDbController.saveSparePart(sparePart);
    }

    @After
    public void deleteAllSpareParts() {
        sparePartsDbController.deleteAllSpareParts();
    }

    @Test
    public void findSparePartByStringWithNumber() {
        SparePart[] spareParts = sparePartsDbController.findSparePart(validTestNumber1);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo(validTestNumber1));
    }

    @Test
    public void findSparePartByStringWithSupplier() {
        SparePart[] spareParts = sparePartsDbController.findSparePart(supplier);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getSupplier(), equalTo(supplier));
    }

    @Test
    public void findSparePartByStringWithProducer() {
        SparePart[] spareParts = sparePartsDbController.findSparePart(producerText);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo(validTestNumber1));
    }

    @Test
    public void findSparePartByStringWithDescription() {
        SparePart[] spareParts = sparePartsDbController.findSparePart(descText);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo(validTestNumber1));
    }

    @Test
    public void findSparePartByStringWithType() {
        SparePart[] spareParts = sparePartsDbController.findSparePart(typeText);

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo(validTestNumber1));
    }
}