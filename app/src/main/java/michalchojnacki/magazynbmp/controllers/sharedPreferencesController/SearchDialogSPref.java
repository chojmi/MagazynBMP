package michalchojnacki.magazynbmp.controllers.sharedPreferencesController;

import android.content.Context;
import android.content.SharedPreferences;

import michalchojnacki.magazynbmp.model.SearchDialogModel;

public class SearchDialogSPref {

    private final String SHARED_PREFERENCES = "searchDialogSharedPreferences";
    private final String NUMBER_CHECKBOX = "numberCheckbox";
    private final String NUMBER_VALUE = "numberValue";
    private final String DESCRIPTION_CHECKBOX = "descriptionCheckbox";
    private final String DESCRIPTION_VALUE = "descriptionValue";
    private final String TYPE_CHECKBOX = "typeCheckbox";
    private final String TYPE_VALUE = "typeValue";
    private final String PRODUCER_CHECKBOX = "producerCheckbox";
    private final String PRODUCER_VALUE = "producerValue";
    private final String SUPPLIER_CHECKBOX = "supplierCheckbox";
    private final String SUPPLIER_VALUE = "supplierValue";

    private final SearchDialogModel mSearchDialogModel;

    public SearchDialogSPref(SearchDialogModel searchDialogModel) {
        mSearchDialogModel = searchDialogModel;
    }

    public void saveToSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(NUMBER_VALUE, mSearchDialogModel.getPartNumber())
                .putBoolean(NUMBER_CHECKBOX, mSearchDialogModel.isPartNumberChecked())
                .putString(DESCRIPTION_VALUE, mSearchDialogModel.getPartDescription())
                .putBoolean(DESCRIPTION_CHECKBOX, mSearchDialogModel.isPartDescriptionChecked())
                .putString(TYPE_VALUE, mSearchDialogModel.getPartType())
                .putBoolean(TYPE_CHECKBOX, mSearchDialogModel.isPartTypeChecked())
                .putString(PRODUCER_VALUE, mSearchDialogModel.getPartProducer())
                .putBoolean(PRODUCER_CHECKBOX, mSearchDialogModel.isPartProducerChecked())
                .putString(SUPPLIER_VALUE, mSearchDialogModel.getPartSupplier())
                .putBoolean(SUPPLIER_CHECKBOX, mSearchDialogModel.isPartSupplierChecked())
                .apply();
    }

    public void readFromSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mSearchDialogModel.setPartNumberChecked(sharedPreferences.getBoolean(NUMBER_CHECKBOX, mSearchDialogModel.isPartNumberChecked()));
        mSearchDialogModel.setPartNumber(sharedPreferences.getString(NUMBER_VALUE, mSearchDialogModel.getPartNumber()));
        mSearchDialogModel.setPartDescriptionChecked(sharedPreferences.getBoolean(DESCRIPTION_CHECKBOX, mSearchDialogModel.isPartDescriptionChecked()));
        mSearchDialogModel.setPartDescription(sharedPreferences.getString(DESCRIPTION_VALUE, mSearchDialogModel.getPartDescription()));
        mSearchDialogModel.setPartTypeChecked(sharedPreferences.getBoolean(TYPE_CHECKBOX, mSearchDialogModel.isPartTypeChecked()));
        mSearchDialogModel.setPartType(sharedPreferences.getString(TYPE_VALUE, mSearchDialogModel.getPartType()));
        mSearchDialogModel.setPartProducerChecked(sharedPreferences.getBoolean(PRODUCER_CHECKBOX, mSearchDialogModel.isPartProducerChecked()));
        mSearchDialogModel.setPartProducer(sharedPreferences.getString(PRODUCER_VALUE, mSearchDialogModel.getPartProducer()));
        mSearchDialogModel.setPartSupplierChecked(sharedPreferences.getBoolean(SUPPLIER_CHECKBOX, mSearchDialogModel.isPartSupplierChecked()));
        mSearchDialogModel.setPartSupplier(sharedPreferences.getString(SUPPLIER_VALUE, mSearchDialogModel.getPartSupplier()));
    }
}
