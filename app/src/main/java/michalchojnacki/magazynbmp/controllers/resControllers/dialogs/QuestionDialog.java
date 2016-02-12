package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import michalchojnacki.magazynbmp.R;

public class QuestionDialog extends DialogFragment {

    private static final String TITLE = "TITLE";
    private static final String QUESTION = "QUESTION";
    private DialogInterface.OnClickListener positiveClickListener;
    private DialogInterface.OnClickListener negativeClickListener;

    public static QuestionDialog newInstance(String dialogTitle, String dialogQuestion) {
        QuestionDialog f = new QuestionDialog();

        Bundle args = new Bundle();
        args.putString(TITLE, dialogTitle);
        args.putString(QUESTION, dialogQuestion);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE);
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(getArguments().getString(QUESTION))
                .setPositiveButton(getContext().getString(R.string.OkLabel), positiveClickListener)
                .setNeutralButton(getContext().getString(R.string.CancelLabel), null)
                .setNegativeButton("No", negativeClickListener)
                .create();
    }

    public QuestionDialog setPositiveClickListener(DialogInterface.OnClickListener onClickListener) {
        positiveClickListener = onClickListener;
        return this;
    }

    public QuestionDialog setNegativeClickListener(DialogInterface.OnClickListener onClickListener) {
        negativeClickListener = onClickListener;
        return this;
    }

    public void showDialog(Context context) {
        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        fm.beginTransaction().add(this, "dialog").commitAllowingStateLoss();
    }
}
