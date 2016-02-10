package michalchojnacki.magazynbmp.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SparePartTest {

    @Test
    public void properGettersValuesWhenEmpty() {
        SparePart sparePart = new SparePart.Builder().build();

        assertTrue(sparePart.getNumber() == null
                && sparePart.getDescription() == null
                && sparePart.getType() == null
                && sparePart.getProducer() == null
                && sparePart.getSupplier() == null
                && sparePart.getLocation() == null);
    }

    @Test
    public void properGettersValues() {
        SparePart sparePart = new SparePart.Builder()
                .number("number")
                .description("description")
                .type("type")
                .producer("producer")
                .location("location")
                .supplier("supplier")
                .build();

        assertTrue(sparePart.getNumber().equals("number")
                && sparePart.getDescription().equals("description")
                && sparePart.getType().equals("type")
                && sparePart.getProducer().equals("producer")
                && sparePart.getSupplier().equals("supplier")
                && sparePart.getLocation().equals("location"));

    }

}