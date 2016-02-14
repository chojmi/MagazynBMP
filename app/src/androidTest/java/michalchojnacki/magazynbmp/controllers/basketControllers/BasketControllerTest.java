package michalchojnacki.magazynbmp.controllers.basketControllers;

import org.junit.Before;
import org.junit.Test;

import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasketControllerTest {

    private BasketController mBasketController;
    private int size = 5;
    private SparePart[] mSpareParts;

    @Before
    public void initTest() {
        mBasketController = new BasketController();
        mSpareParts = new SparePart[size];

        for (int i = 0; i < size; i++) {
            mSpareParts[i] = new SparePart.Builder()
                    .number("number " + i)
                    .description("description " + i)
                    .type("type " + i)
                    .producer("producer " + i)
                    .location("location " + i)
                    .supplier("supplier " + i)
                    .build();
        }
    }

    @Test
    public void testUpdateBasket() throws Exception {
        mBasketController.addToBasket(mSpareParts[0], 1);
        mBasketController.addToBasket(mSpareParts[0], 1);

        for (int i = 0; i < mBasketController.size(); i++) {
            assertThat(mBasketController.getSparePart(i).getNumber(), equalTo(mSpareParts[0].getNumber()));
            assertThat(mBasketController.getQuantity(i), equalTo(1));
        }

        mBasketController.updateBasket(mSpareParts[0], 4, 1);

        assertThat(mBasketController.getSparePart(0).getNumber(), equalTo(mSpareParts[0].getNumber()));
        assertThat(mBasketController.getQuantity(0), equalTo(1));
        assertThat(mBasketController.getSparePart(1).getNumber(), equalTo(mSpareParts[0].getNumber()));
        assertThat(mBasketController.getQuantity(1), equalTo(4));

        mBasketController.updateBasket(mSpareParts[0], 9, 1);

        assertThat(mBasketController.getSparePart(0).getNumber(), equalTo(mSpareParts[0].getNumber()));
        assertThat(mBasketController.getQuantity(0), equalTo(4));
        assertThat(mBasketController.getSparePart(1).getNumber(), equalTo(mSpareParts[0].getNumber()));
        assertThat(mBasketController.getQuantity(01), equalTo(9));
    }

    @Test
    public void testDeleteSparePart() throws Exception {
        mBasketController.addToBasket(mSpareParts[0], 1);
        mBasketController.addToBasket(mSpareParts[0], 1);

        for (int i = 0; i < mBasketController.size(); i++) {
            assertThat(mBasketController.getSparePart(i).getNumber(), equalTo(mSpareParts[0].getNumber()));
            assertThat(mBasketController.getQuantity(i), equalTo(1));
        }

        mBasketController.deleteSparePart(mSpareParts[0], 1);

        assertThat(mBasketController.getSparePart(0).getNumber(), equalTo(mSpareParts[0].getNumber()));
        assertThat(mBasketController.getQuantity(0), equalTo(1));

        mBasketController.deleteSparePart(mSpareParts[0], 1);
        assertThat(mBasketController.size(), equalTo(0));
    }

    @Test
    public void testAddToBasket() throws Exception {
        int i = 0;

        for (SparePart sparePart : mSpareParts) {
            mBasketController.addToBasket(sparePart, i++);
        }

        i = 0;
        for (SparePart sparePart : mSpareParts) {
            assertThat(mBasketController.getQuantity(i), equalTo(i));
            assertThat(mBasketController.getSparePart(i).getNumber(), equalTo(mSpareParts[i].getNumber()));
            assertThat(mBasketController.getSparePart(i).getSupplier(), equalTo(mSpareParts[i].getSupplier()));
            assertThat(mBasketController.getSparePart(i).getDescription(), equalTo(mSpareParts[i].getDescription()));
            assertThat(mBasketController.getSparePart(i).getLocation(), equalTo(mSpareParts[i].getLocation()));
            assertThat(mBasketController.getSparePart(i).getProducer(), equalTo(mSpareParts[i].getProducer()));
            assertThat(mBasketController.getSparePart(i).getType(), equalTo(mSpareParts[i].getType()));
        }
    }

    @Test
    public void testClear() throws Exception {
        for (SparePart sparePart : mSpareParts) {
            mBasketController.addToBasket(sparePart, 1);
        }

        mBasketController.clear();

        assertThat(mBasketController.size(), equalTo(0));
    }

    @Test
    public void testSize() throws Exception {
        int i = 0;

        for (SparePart sparePart : mSpareParts) {
            mBasketController.addToBasket(sparePart, i++);
        }

        assertThat(mBasketController.size(), equalTo(i));
    }
}