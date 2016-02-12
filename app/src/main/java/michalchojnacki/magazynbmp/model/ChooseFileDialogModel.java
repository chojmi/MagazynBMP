package michalchojnacki.magazynbmp.model;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import michalchojnacki.magazynbmp.R;

public class ChooseFileDialogModel {

    private final EditText mNumberColumnEditText;
    private final CheckableTextView mTypeColumn;
    private final CheckableTextView mDescriptionColumn;
    private final CheckableTextView mProducerColumn;
    private final CheckableTextView mSupplierColumn;
    private final CheckableTextView mLocationColumn;
    private final TextView mPartPrefix;
    private final TextView mSheetNameText;
    private final TextView mChosenFile;
    private String mChosenFilePath;

    public ChooseFileDialogModel(View view) {
        mSheetNameText = (TextView) view.findViewById(R.id.DialogChooseSheetNameText);
        mChosenFile = (TextView) view.findViewById(R.id.DialogChosenFileNameTextView);
        mPartPrefix = (EditText) view.findViewById(R.id.DialogChoosePartPrefixText);
        mNumberColumnEditText = (EditText) view.findViewById(R.id.DialogChooseNumberColumnText);
        mTypeColumn = new CheckableTextView((EditText) view.findViewById(R.id.DialogChooseTypeColumnText),
                (CheckBox) view.findViewById(R.id.DialogChooseTypeColumnCheckbox));
        mDescriptionColumn = new CheckableTextView((EditText) view.findViewById(R.id.DialogChooseDescriptionColumnText),
                (CheckBox) view.findViewById(R.id.DialogChooseDescriptionColumnCheckbox));
        mProducerColumn = new CheckableTextView((EditText) view.findViewById(R.id.DialogChooseProducerColumnText),
                (CheckBox) view.findViewById(R.id.DialogChooseProducerColumnCheckbox));
        mSupplierColumn = new CheckableTextView((EditText) view.findViewById(R.id.DialogChooseSupplierColumnText),
                (CheckBox) view.findViewById(R.id.DialogChooseSupplierColumnCheckBox));
        mLocationColumn = new CheckableTextView((EditText) view.findViewById(R.id.DialogChooseLocationColumnText),
                (CheckBox) view.findViewById(R.id.DialogChooseLocationColumnCheckBox));

    }

    public void setDefaultValues(Context context) {
        setPartPrefix(context.getResources().getString(R.string.DefaultPartPrefix));
        setSheetName(context.getResources().getString(R.string.DefaultSheetName));
        setNumberColumn(context.getResources().getString(R.string.DefaultNumberColumn));
        setTypeColumnChecked(true);
        setTypeColumn(context.getResources().getString(R.string.DefaultTypeColumn));
        setDescriptionColumnChecked(true);
        setDescriptionColumn(context.getResources().getString(R.string.DefaultDescriptionColumn));
        setProducerColumnChecked(true);
        setProducerColumn(context.getResources().getString(R.string.DefaultProducerColumn));
        setSupplierColumnChecked(true);
        setSupplierColumn(context.getResources().getString(R.string.DefaultSupplierColumn));
        setLocationColumnChecked(true);
        setLocationColumn(context.getResources().getString(R.string.DefaultLocationColumn));
    }

    public void setNumberColumn(String text) {
        mNumberColumnEditText.setText(text.trim());
    }

    public String getChosenFilePath() {
        return mChosenFilePath;
    }

    public void setChosenFilePath(String path) {
        if (path != null && !getChosenFileText().equals(path)) {
            mChosenFilePath = path;
            setChosenFileText(Uri.parse(path).getLastPathSegment());
        }
    }

    public String getChosenFileText() {
        return getTextViewString(mChosenFile);
    }

    private void setChosenFileText(String text) {
        mChosenFile.setText(text.trim());
    }

    private String getTextViewString(TextView textView) {
        return textView.getText().toString().trim();
    }

    public String getColumnNumber() {
        return getTextViewString(mNumberColumnEditText);
    }

    public String getDescriptionColumn() {
        return getText(mDescriptionColumn);
    }

    public void setDescriptionColumn(String text) {
        mDescriptionColumn.setText(text);
    }

    private String getText(CheckableTextView checkableTextView) {
        return checkableTextView.getText();
    }

    public String getDescriptionColumnIfChecked() {
        return getTextIfChecked(mDescriptionColumn);
    }

    private String getTextIfChecked(CheckableTextView checkableTextView) {
        if (checkableTextView.isChecked()) {
            return getText(checkableTextView);
        }
        return null;
    }

    public String getLocationColumn() {
        return getText(mLocationColumn);
    }

    public void setLocationColumn(String text) {
        mLocationColumn.setText(text);
    }

    public String getLocationColumnIfChecked() {
        return getTextIfChecked(mLocationColumn);
    }

    public String getPartPrefix() {
        return getTextViewString(mPartPrefix);
    }

    public void setPartPrefix(String partPrefix) {
        mPartPrefix.setText(partPrefix.trim());
    }

    public String getProducerColumn() {
        return getText(mProducerColumn);
    }

    public void setProducerColumn(String text) {
        mProducerColumn.setText(text);
    }

    public String getSupplierColumn() {
        return getText(mSupplierColumn);
    }

    public void setSupplierColumn(String text) {
        mSupplierColumn.setText(text);
    }

    public String getProducerColumnIfChecked() {
        return getTextIfChecked(mProducerColumn);
    }

    public String getSupplierColumnIfChecked() {
        return getTextIfChecked(mSupplierColumn);
    }

    public String getSheetName() {
        return getTextViewString(mSheetNameText);
    }

    public void setSheetName(String text) {
        mSheetNameText.setText(text.trim());
    }

    public String getTypeColumn() {
        return getText(mTypeColumn);
    }

    public void setTypeColumn(String text) {
        mTypeColumn.setText(text);
    }

    public String getTypeColumnIfChecked() {
        return getTextIfChecked(mTypeColumn);
    }

    public boolean isDescriptionColumnChecked() {
        return mDescriptionColumn.isChecked();
    }

    public void setDescriptionColumnChecked(boolean value) {
        mDescriptionColumn.setChecked(value);
    }

    public boolean isLocationColumnChecked() {
        return mLocationColumn.isChecked();
    }

    public void setLocationColumnChecked(boolean value) {
        mLocationColumn.setChecked(value);
    }

    public boolean isProducerColumnChecked() {
        return mProducerColumn.isChecked();
    }

    public void setProducerColumnChecked(boolean value) {
        mProducerColumn.setChecked(value);
    }

    public boolean isTypeColumnChecked() {
        return mTypeColumn.isChecked();
    }

    public void setTypeColumnChecked(boolean value) {
        mTypeColumn.setChecked(value);
    }

    public boolean isSupplierColumnChecked() {
        return mSupplierColumn.isChecked();
    }

    public void setSupplierColumnChecked(boolean value) {
        mSupplierColumn.setChecked(value);
    }
}
