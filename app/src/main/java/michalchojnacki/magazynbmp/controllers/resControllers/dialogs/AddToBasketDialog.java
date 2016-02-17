package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.SparePartViewer;
import michalchojnacki.magazynbmp.controllers.sharedPreferencesController.AddToBasketDialogSPref;
import michalchojnacki.magazynbmp.model.SparePart;

public class AddToBasketDialog extends DialogFragment {

    private static final String SPARE_PART = "sparePart";

    private SparePart mSparePart;
    private Context mContext;
    private TextView mSparePartNumber;
    private EditText mSparePartQuantity;
    private AddToBasketDialogSPref mAddToBasketDialogSPref;

    public static AddToBasketDialog newInstance(SparePart sparePart) {
        AddToBasketDialog f = new AddToBasketDialog();

        Bundle args = new Bundle();
        args.putSerializable(SPARE_PART, sparePart);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_basket, null);
        mSparePart = (SparePart) getArguments().getSerializable(SPARE_PART);

        mSparePartNumber = (TextView) view.findViewById(R.id.changeBasketSparePart);
        mSparePartNumber.setText(mSparePart.getNumber());
        mSparePartQuantity = (EditText) view.findViewById(R.id.changeBasketQuantity);
        mAddToBasketDialogSPref = new AddToBasketDialogSPref(mSparePartQuantity);
        mAddToBasketDialogSPref.readFromSPref(mContext);

        Dialog dialog = initializeAlertDialog(view);
        dialog.getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return dialog;
    }

    private AlertDialog initializeAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mContext.getString(R.string.AddSpPartToBasket))
                .setView(view)
                .setPositiveButton(mContext.getString(R.string.AddLabel), null)
                .setNegativeButton(R.string.CancelLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddToBasketDialog.this.getDialog().cancel();
                    }
                });
        return initializeAlertDialogButtons(builder.create());
    }

    private AlertDialog initializeAlertDialogButtons(final AlertDialog alertDialog) {
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!mSparePartQuantity.getText().toString().equals("")) {
                                    ((SparePartViewer) getActivity()).addToBasket(
                                            new Integer(mSparePartQuantity.getText().toString()));
                                    mAddToBasketDialogSPref.saveToSPref(mContext);
                                    dismissAllowingStateLoss();
                                } else {
                                    ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                                                            mContext.getString(
                                                                    R.string.QuantityEmptyLabel))
                                            .showDialog(mContext);
                                }

                            }
                        });
            }
        });
        return alertDialog;
    }
}
