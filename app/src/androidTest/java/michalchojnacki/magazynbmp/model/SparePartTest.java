package michalchojnacki.magazynbmp.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SparePartTest {

    private String numberText = "number";
    private String locationText = "location";
    private String descText = "desc";
    private String producerText = "producer";
    private String typeText = "type";
    private String supplierText = "supplier";

    @Test
    public void properGettersValuesWhenEmpty() {
        SparePart sparePart = new SparePart.Builder().build();

        assertTrue(
                sparePart.getNumber() == null && sparePart.getDescription() == null && sparePart.getType() == null && sparePart
                        .getProducer() == null && sparePart.getSupplier() == null && sparePart.getLocation() == null);
    }

    @Test
    public void properGettersValues() {
        SparePart sparePart = new SparePart.Builder().number(numberText)
                .description(descText)
                .type(typeText)
                .producer(producerText)
                .location(locationText)
                .supplier(supplierText)
                .build();

        assertTrue(sparePart.getNumber().equals(numberText) && sparePart.getDescription()
                .equals(descText) && sparePart.getType().equals(typeText) && sparePart.getProducer()
                .equals(producerText) && sparePart.getSupplier()
                .equals(supplierText) && sparePart.getLocation().equals(locationText));

    }

}