package michalchojnacki.magazynbmp.model;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import michalchojnacki.magazynbmp.R;

public class SimpleSearchDialogModel {

    private final TextView mSearchText;

    public SimpleSearchDialogModel(View view) {
        mSearchText = (EditText) view.findViewById(R.id.DialogSimpleSearchText);
    }

    public void setDefaultValues(Context context) {
        setSearchText(context.getString(R.string.SimpleSearchText));
    }

    public String getSearchText() {
        return mSearchText.getText().toString();
    }

    public void setSearchText(String text) {
        mSearchText.setText(text.trim());
    }
}
