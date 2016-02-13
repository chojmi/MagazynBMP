package michalchojnacki.magazynbmp.controllers.basketControllers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import michalchojnacki.magazynbmp.model.SparePart;

public class BasketController implements Serializable {

    private List<SparePartWithQuantity> mSparePartsWithQuantities = new LinkedList<>();

    public void addToBasket(SparePart sparePart, int quantity) {
        deleteSparePart(sparePart);
        mSparePartsWithQuantities.add(new SparePartWithQuantity(sparePart).setQuantity(quantity));

    }

    public void deleteSparePart(SparePart sparePart) {
        for (Iterator<SparePartWithQuantity> it = mSparePartsWithQuantities.iterator(); it.hasNext(); ) {
            SparePartWithQuantity sparePartWithQuantity = it.next();
            if (sparePartWithQuantity.getSparePart().getNumber().equals(sparePart.getNumber())) {
                it.remove();
            }
        }
    }

    public void clear() {
        mSparePartsWithQuantities.clear();
    }

    public int size() {
        return mSparePartsWithQuantities.size();
    }

    public SparePart getSparePart(int index) {
        return mSparePartsWithQuantities.get(index).getSparePart();
    }

    public int getQuantity(int index) {
        return mSparePartsWithQuantities.get(index).getQuantity();
    }

    class SparePartWithQuantity implements Serializable {

        private SparePart mSparePart;
        private int quantity = 0;

        public SparePartWithQuantity(SparePart sparePart) {
            mSparePart = sparePart;
        }

        public int getQuantity() {
            return quantity;
        }

        public SparePartWithQuantity setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public SparePart getSparePart() {
            return mSparePart;
        }
    }
}
