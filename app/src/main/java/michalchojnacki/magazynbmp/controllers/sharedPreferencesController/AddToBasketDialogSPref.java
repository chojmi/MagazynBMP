package michalchojnacki.magazynbmp.controllers.sharedPreferencesController;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class AddToBasketDialogSPref {

    private final String SHARED_PREFERENCES = "addToBasketDialogSharedPreferences";
    private final String QUANTITY = "quantity";
    private EditText mQuantity;

    public AddToBasketDialogSPref(EditText quantity) {
        mQuantity = quantity;
    }

    public void saveToSPref(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(QUANTITY, Integer.valueOf(mQuantity.getText().toString()))
                .apply();
    }

    public void readFromSPref(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        int quantity = sharedPreferences.getInt(QUANTITY, -1);
        if (quantity >= 0) {
            mQuantity.setText(String.valueOf(quantity));
            mQuantity.setSelectAllOnFocus(true);
        }
    }
}
