package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import michalchojnacki.magazynbmp.R;

public class ErrorDialog extends DialogFragment {

    private static final String TITLE = "TITLE";
    private static final String MESSAGE = "MESSAGE";

    public static ErrorDialog newInstance(String dialogTitle, String dialogMessage) {
        ErrorDialog f = new ErrorDialog();

        Bundle args = new Bundle();
        args.putString(TITLE, dialogTitle);
        args.putString(MESSAGE, dialogMessage);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE);
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(getArguments().getString(MESSAGE))
                .setPositiveButton(getContext().getString(R.string.OkLabel), null)
                .create();
    }

    public void showDialog(Context context) {
        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        fm.beginTransaction().add(this, "dialog").commitAllowingStateLoss();
    }
}
