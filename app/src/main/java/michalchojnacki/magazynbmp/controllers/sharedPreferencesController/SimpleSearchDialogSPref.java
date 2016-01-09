package michalchojnacki.magazynbmp.controllers.sharedPreferencesController;

import android.content.Context;
import android.content.SharedPreferences;

import michalchojnacki.magazynbmp.model.SimpleSearchDialogModel;

public class SimpleSearchDialogSPref {

    private final String SHARED_PREFERENCES = "SimpleSearchDialogSharedPreferences";
    private final String SEARCH_TEXT = "searchText";
    private SimpleSearchDialogModel mSimpleSearchDialogModel;

    public SimpleSearchDialogSPref(SimpleSearchDialogModel simpleSearchDialogModel) {
        mSimpleSearchDialogModel = simpleSearchDialogModel;
    }

    public void saveToSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(SEARCH_TEXT, mSimpleSearchDialogModel.getSearchText())
                .apply();
    }

    public void readFromSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mSimpleSearchDialogModel.setSearchText(sharedPreferences.getString(SEARCH_TEXT, mSimpleSearchDialogModel.getSearchText()));
    }
}
