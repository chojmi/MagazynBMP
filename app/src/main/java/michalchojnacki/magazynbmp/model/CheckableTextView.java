package michalchojnacki.magazynbmp.model;

import android.widget.CheckBox;
import android.widget.TextView;

class CheckableTextView {

    private final TextView mTextView;
    private final CheckBox mCheckBox;

    public CheckableTextView(TextView textView, CheckBox checkBox) {
        mTextView = textView;
        mCheckBox = checkBox;
    }

    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    public void setChecked(boolean value) {
        mCheckBox.setChecked(value);
    }

    public String getText() {
        return mTextView.getText().toString().trim();
    }

    public void setText(String text) {
        mTextView.setText(text.trim());
    }
}