package michalchojnacki.magazynbmp.controllers.sharedPreferencesController;

import android.content.Context;
import android.content.SharedPreferences;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.BasketController;
import michalchojnacki.magazynbmp.model.SparePart;

public class BasketControllerSPref {

    private final String SHARED_PREFERENCES = "basketControllerSharedPreferences";
    private final String BASKET_SIZE = "basketSize";
    private final String NUMBER = "number";
    private final String DESCRIPTION = "description";
    private final String TYPE = "type";
    private final String PRODUCER = "producer";
    private final String SUPPLIER = "supplier";
    private final String LOCATION = "location";
    private final String QUANTITY = "quantity";

    public void saveToSPref(Context context, BasketController basketController) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear()
                .putInt(BASKET_SIZE, basketController.size());

        for (int i = 0; i < basketController.size(); i++) {
            putNextSparePartToShPref(basketController, editor, i);
        }
        editor.apply();
    }

    private void putNextSparePartToShPref(BasketController basketController,
                                          SharedPreferences.Editor editor, int index) {
        SparePart sparePart = basketController.getSparePart(index);
        editor.putString(NUMBER + index, sparePart.getNumber())
                .putString(DESCRIPTION + index, sparePart.getDescription())
                .putString(TYPE + index, sparePart.getType())
                .putString(PRODUCER + index, sparePart.getProducer())
                .putString(SUPPLIER + index, sparePart.getSupplier())
                .putString(LOCATION + index, sparePart.getLocation())
                .putInt(QUANTITY + index, basketController.getQuantity(index));
    }

    public BasketController readFromSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        BasketController basketController = new BasketController();
        int size = sharedPreferences.getInt(BASKET_SIZE, -1);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                basketController.addToBasket(getSparePartFromShPref(context, sharedPreferences, i),
                                             sharedPreferences.getInt(QUANTITY + i, 0));
            }
        }
        return basketController;
    }

    private SparePart getSparePartFromShPref(Context context, SharedPreferences sharedPreferences,
                                             int index) {
        return new SparePart.Builder().number(sharedPreferences.getString(NUMBER + index,
                                                                          context.getString(
                                                                                  R.string.NoDataLabel)))
                .description(sharedPreferences.getString(DESCRIPTION + index,
                                                         context.getString(R.string.NoDataLabel)))
                .type(sharedPreferences.getString(TYPE + index,
                                                  context.getString(R.string.NoDataLabel)))
                .producer(sharedPreferences.getString(PRODUCER + index,
                                                      context.getString(R.string.NoDataLabel)))
                .supplier(sharedPreferences.getString(SUPPLIER + index,
                                                      context.getString(R.string.NoDataLabel)))
                .location(sharedPreferences.getString(LOCATION + index,
                                                      context.getString(R.string.NoDataLabel)))
                .build();
    }
}
