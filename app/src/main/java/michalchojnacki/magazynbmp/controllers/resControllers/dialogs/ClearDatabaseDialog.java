package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import michalchojnacki.magazynbmp.R;

public class ClearDatabaseDialog extends DialogFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof ClearDatabaseListener)) {
            throw new ClassCastException(activity.toString() + " must implement ClearDatabaseListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.AreYouSureLabel)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((ClearDatabaseListener) getActivity()).clearDatabase();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();
    }


    public interface ClearDatabaseListener {

        void clearDatabase();
    }
}
