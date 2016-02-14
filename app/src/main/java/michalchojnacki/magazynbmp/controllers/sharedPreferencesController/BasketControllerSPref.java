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
            SparePart sparePart = basketController.getSparePart(i);
            editor.putString(NUMBER + i, sparePart.getNumber())
                    .putString(DESCRIPTION + i, sparePart.getDescription())
                    .putString(TYPE + i, sparePart.getType())
                    .putString(PRODUCER + i, sparePart.getProducer())
                    .putString(SUPPLIER + i, sparePart.getSupplier())
                    .putString(LOCATION + i, sparePart.getLocation())
                    .putInt(QUANTITY + i, basketController.getQuantity(i));
        }
        editor.apply();
    }

    public BasketController readFromSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        BasketController basketController = new BasketController();
        int size = sharedPreferences.getInt(BASKET_SIZE, -1);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                basketController.addToBasket(new SparePart.Builder()
                        .number(sharedPreferences.getString(NUMBER + i, context.getString(R.string.NoDataLabel)))
                        .description(sharedPreferences.getString(DESCRIPTION + i, context.getString(R.string.NoDataLabel)))
                        .type(sharedPreferences.getString(TYPE + i, context.getString(R.string.NoDataLabel)))
                        .producer(sharedPreferences.getString(PRODUCER + i, context.getString(R.string.NoDataLabel)))
                        .supplier(sharedPreferences.getString(SUPPLIER + i, context.getString(R.string.NoDataLabel)))
                        .location(sharedPreferences.getString(LOCATION + i, context.getString(R.string.NoDataLabel)))
                        .build(), sharedPreferences.getInt(QUANTITY + i, 0));
            }
        }
        return basketController;
    }
}
