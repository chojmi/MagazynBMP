package michalchojnacki.magazynbmp.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import michalchojnacki.magazynbmp.R;

public class SearchDialogModel {

    private final CheckableTextView mPartNumber;
    private final CheckableTextView mPartType;
    private final CheckableTextView mPartDescription;
    private final CheckableTextView mPartProducer;
    private final CheckableTextView mPartSupplier;

    public SearchDialogModel(View view) {

        mPartNumber = new CheckableTextView((EditText) view.findViewById(R.id.searchingPartNumber),
                                            (CheckBox) view.findViewById(
                                                    R.id.searchingPartNumberCheckbox));
        mPartType = new CheckableTextView((EditText) view.findViewById(R.id.DialogSearchTypeText),
                                          (CheckBox) view.findViewById(
                                                  R.id.DialogSearchTypeCheckbox));
        mPartDescription =
                new CheckableTextView((EditText) view.findViewById(R.id.searchingPartDescription),
                                      (CheckBox) view.findViewById(
                                              R.id.searchingPartDescriptionCheckbox));
        mPartProducer =
                new CheckableTextView((EditText) view.findViewById(R.id.DialogSearchProducerText),
                                      (CheckBox) view.findViewById(
                                              R.id.DialogSearchProducerCheckbox));
        mPartSupplier =
                new CheckableTextView((EditText) view.findViewById(R.id.DialogSearchSupplierText),
                                      (CheckBox) view.findViewById(
                                              R.id.DialogSearchSupplierCheckbox));
    }

    public String getPartDescription() {
        return getText(mPartDescription);
    }

    public void setPartDescription(String partDescription) {
        mPartDescription.setText(partDescription);
    }

    private String getText(CheckableTextView checkableTextView) {
        return checkableTextView.getText();
    }

    public String getPartSupplier() {
        return getText(mPartSupplier);
    }

    public void setPartSupplier(String partSupplier) {
        mPartSupplier.setText(partSupplier);
    }

    public String getPartDescriptionIfChecked() {
        return getTextIfChecked(mPartDescription);
    }

    private String getTextIfChecked(CheckableTextView checkableTextView) {
        if (checkableTextView.isChecked()) {
            return getText(checkableTextView);
        }
        return null;
    }

    public String getPartSupplierIfChecked() {
        return getTextIfChecked(mPartSupplier);
    }

    public String getPartNumber() {
        return getText(mPartNumber);
    }

    public void setPartNumber(String partNumber) {
        mPartNumber.setText(partNumber);
    }

    public String getPartNumberIfChecked() {
        return getTextIfChecked(mPartNumber);
    }

    public String getPartProducer() {
        return getText(mPartProducer);
    }

    public void setPartProducer(String partProducer) {
        mPartProducer.setText(partProducer);
    }

    public String getPartProducerIfChecked() {
        return getTextIfChecked(mPartProducer);
    }

    public String getPartType() {
        return getText(mPartType);
    }

    public void setPartType(String partType) {
        mPartType.setText(partType);
    }

    public String getPartTypeIfChecked() {
        return getTextIfChecked(mPartType);
    }

    public boolean isPartDescriptionChecked() {
        return mPartDescription.isChecked();
    }

    public void setPartDescriptionChecked(boolean value) {
        mPartDescription.setChecked(value);
    }

    public boolean isPartSupplierChecked() {
        return mPartSupplier.isChecked();
    }

    public void setPartSupplierChecked(boolean value) {
        mPartSupplier.setChecked(value);
    }


    public boolean isPartNumberChecked() {
        return mPartNumber.isChecked();
    }

    public void setPartNumberChecked(boolean value) {
        mPartNumber.setChecked(value);
    }

    public boolean isPartProducerChecked() {
        return mPartProducer.isChecked();
    }

    public void setPartProducerChecked(boolean value) {
        mPartProducer.setChecked(value);
    }

    public boolean isPartTypeChecked() {
        return mPartType.isChecked();
    }

    public void setPartTypeChecked(boolean value) {
        mPartType.setChecked(value);
    }

    public void setDefaultValues() {

        setPartNumberChecked(false);
        setPartNumber("");
        setPartTypeChecked(false);
        setPartType("");
        setPartDescriptionChecked(false);
        setPartDescription("");
        setPartProducerChecked(false);
        setPartProducer("");
        setPartSupplierChecked(false);
        setPartSupplier("");
    }
}
