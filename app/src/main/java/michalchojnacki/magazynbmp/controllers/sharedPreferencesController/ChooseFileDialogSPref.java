package michalchojnacki.magazynbmp.controllers.sharedPreferencesController;

import android.content.Context;
import android.content.SharedPreferences;

import michalchojnacki.magazynbmp.model.ChooseFileDialogModel;

public class ChooseFileDialogSPref {

    private final String SHARED_PREFERENCES = "chooseFileSharedPreferences";
    private final String FILE_PATH = "filePath";
    private final String SHEET_NAME = "sheetName";
    private final String PREFIX = "prefix";
    private final String COLUMN_OF_NUMBER = "columnOfNumber";
    private final String COLUMN_OF_TYPE = "columnOfType";
    private final String COLUMN_OF_DESCRIPTION = "columnOfDescription";
    private final String COLUMN_OF_PRODUCER = "columnOfProducer";
    private final String COLUMN_OF_LOCATION = "columnOfLocation";
    private final String COLUMN_OF_SUPPLIER = "columnOfSupplier";
    private final String COLUMN_OF_TYPE_CHECKBOX = "columnOfTypeCheckbox";
    private final String COLUMN_OF_DESCRIPTION_CHECKBOX = "columnOfDescriptionCheckbox";
    private final String COLUMN_OF_PRODUCER_CHECKBOX = "columnOfProducerCheckbox";
    private final String COLUMN_OF_LOCATION_CHECKBOX = "columnOfLocationCheckbox";
    private final String COLUMN_OF_SUPPLIER_CHECKBOX = "columnOfSupplierCheckbox";


    private final ChooseFileDialogModel mChooseFileDialogModel;

    public ChooseFileDialogSPref(ChooseFileDialogModel chooseFileDialogModel) {
        mChooseFileDialogModel = chooseFileDialogModel;
    }

    public void saveToSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(FILE_PATH, mChooseFileDialogModel.getChosenFilePath())
                .putString(SHEET_NAME, mChooseFileDialogModel.getSheetName())
                .putString(PREFIX, mChooseFileDialogModel.getPartPrefix())
                .putString(COLUMN_OF_NUMBER, mChooseFileDialogModel.getColumnNumber())
                .putString(COLUMN_OF_TYPE, mChooseFileDialogModel.getTypeColumn())
                .putString(COLUMN_OF_DESCRIPTION, mChooseFileDialogModel.getDescriptionColumn())
                .putString(COLUMN_OF_PRODUCER, mChooseFileDialogModel.getProducerColumn())
                .putString(COLUMN_OF_LOCATION, mChooseFileDialogModel.getLocationColumn())
                .putString(COLUMN_OF_SUPPLIER, mChooseFileDialogModel.getSupplierColumn())
                .putBoolean(COLUMN_OF_TYPE_CHECKBOX, mChooseFileDialogModel.isTypeColumnChecked())
                .putBoolean(COLUMN_OF_DESCRIPTION_CHECKBOX, mChooseFileDialogModel.isDescriptionColumnChecked())
                .putBoolean(COLUMN_OF_PRODUCER_CHECKBOX, mChooseFileDialogModel.isProducerColumnChecked())
                .putBoolean(COLUMN_OF_SUPPLIER_CHECKBOX, mChooseFileDialogModel.isSupplierColumnChecked())
                .apply();
    }

    public void readFromSPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mChooseFileDialogModel.setChosenFilePath(sharedPreferences.getString(FILE_PATH, mChooseFileDialogModel.getChosenFilePath()));
        mChooseFileDialogModel.setSheetName(sharedPreferences.getString(SHEET_NAME, mChooseFileDialogModel.getSheetName()));
        mChooseFileDialogModel.setPartPrefix(sharedPreferences.getString(PREFIX, mChooseFileDialogModel.getPartPrefix()));
        mChooseFileDialogModel.setNumberColumn(sharedPreferences.getString(COLUMN_OF_NUMBER, mChooseFileDialogModel.getColumnNumber()));
        mChooseFileDialogModel.setTypeColumn(sharedPreferences.getString(COLUMN_OF_TYPE, mChooseFileDialogModel.getTypeColumn()));
        mChooseFileDialogModel.setDescriptionColumn(sharedPreferences.getString(COLUMN_OF_DESCRIPTION, mChooseFileDialogModel.getDescriptionColumn()));
        mChooseFileDialogModel.setProducerColumn(sharedPreferences.getString(COLUMN_OF_PRODUCER, mChooseFileDialogModel.getProducerColumn()));
        mChooseFileDialogModel.setLocationColumn(sharedPreferences.getString(COLUMN_OF_LOCATION, mChooseFileDialogModel.getLocationColumn()));
        mChooseFileDialogModel.setSupplierColumn(sharedPreferences.getString(COLUMN_OF_SUPPLIER, mChooseFileDialogModel.getSupplierColumn()));
        mChooseFileDialogModel.setTypeColumnChecked(sharedPreferences.getBoolean(COLUMN_OF_TYPE_CHECKBOX, mChooseFileDialogModel.isTypeColumnChecked()));
        mChooseFileDialogModel.setDescriptionColumnChecked(sharedPreferences.getBoolean(COLUMN_OF_DESCRIPTION_CHECKBOX, mChooseFileDialogModel.isDescriptionColumnChecked()));
        mChooseFileDialogModel.setProducerColumnChecked(sharedPreferences.getBoolean(COLUMN_OF_PRODUCER_CHECKBOX, mChooseFileDialogModel.isProducerColumnChecked()));
        mChooseFileDialogModel.setLocationColumnChecked(sharedPreferences.getBoolean(COLUMN_OF_LOCATION_CHECKBOX, mChooseFileDialogModel.isLocationColumnChecked()));
        mChooseFileDialogModel.setSupplierColumnChecked(sharedPreferences.getBoolean(COLUMN_OF_SUPPLIER_CHECKBOX, mChooseFileDialogModel.isSupplierColumnChecked()));
    }
}
