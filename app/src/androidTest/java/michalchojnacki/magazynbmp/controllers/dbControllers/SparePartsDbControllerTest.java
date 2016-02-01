package michalchojnacki.magazynbmp.controllers.dbControllers;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

;

@RunWith(AndroidJUnit4.class)
public class SparePartsDbControllerTest {

    private SparePartsDbController sparePartsDbController;

    @Before
    public void initSparePartsDbController() {
        sparePartsDbController = new SparePartsDbController(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void countOfEmptyDb() {
        sparePartsDbController.deleteAllSpareParts();
        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(0));
    }

    @Test
    public void testSaveSparePart() {
        SparePart sparePart = new SparePart.Builder().number("YA2020").build();
        sparePartsDbController.saveSparePart(sparePart);
        assertThat(sparePartsDbController.getCountOfSpareParts(), equalTo(1));
    }

}