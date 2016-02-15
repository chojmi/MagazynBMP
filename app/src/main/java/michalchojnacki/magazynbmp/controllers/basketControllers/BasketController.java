package michalchojnacki.magazynbmp.controllers.basketControllers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import michalchojnacki.magazynbmp.model.SparePart;

public class BasketController implements Serializable {

    private List<SparePartWithQuantity> mSparePartsWithQuantities = new LinkedList<>();

    public void updateBasket(SparePart sparePart, int newQuantity, int oldQuantity) {
        deleteSparePart(sparePart, oldQuantity);
        mSparePartsWithQuantities.add(new SparePartWithQuantity(sparePart).setQuantity(newQuantity));
    }

    public void deleteSparePart(SparePart sparePart, int quantity) {
        for (Iterator<SparePartWithQuantity> it = mSparePartsWithQuantities.iterator(); it.hasNext(); ) {
            SparePartWithQuantity sparePartWithQuantity = it.next();
            if (sparePartWithQuantity.equalToSparePart(sparePart, quantity)) {
                it.remove();
                return;
            }
        }
    }

    public void addToBasket(SparePart sparePart, int quantity) {
        mSparePartsWithQuantities.add(new SparePartWithQuantity(sparePart).setQuantity(quantity));
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

        public boolean equalToSparePart(SparePart sparePart, int quantity) {
            return getSparePart().getNumber().equals(sparePart.getNumber())
                    && getQuantity() == quantity;
        }

        public SparePart getSparePart() {
            return mSparePart;
        }

        public int getQuantity() {
            return quantity;
        }

        public SparePartWithQuantity setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
    }
}
