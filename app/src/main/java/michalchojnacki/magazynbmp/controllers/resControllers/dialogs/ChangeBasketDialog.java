package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.basketControllers.QuantityChangedListener;
import michalchojnacki.magazynbmp.model.SparePart;

public class ChangeBasketDialog extends DialogFragment {

    private static final String SPARE_PART = "sparePart";
    private static final String QUANTITY = "quantity";

    private SparePart mSparePart;
    private Context mContext;
    private TextView mSparePartNumber;
    private EditText mSparePartQuantity;

    private View.OnClickListener deleteClick;
    private QuantityChangedListener mQuantityChangedListener;

    public static ChangeBasketDialog newInstance(SparePart sparePart, int quantity) {
        ChangeBasketDialog f = new ChangeBasketDialog();

        Bundle args = new Bundle();
        args.putSerializable(SPARE_PART, sparePart);
        args.putInt(QUANTITY, quantity);
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
        int quantity = getArguments().getInt(QUANTITY);

        mSparePartNumber = (TextView) view.findViewById(R.id.changeBasketSparePart);
        mSparePartNumber.setText(mSparePart.getNumber());
        mSparePartQuantity = (EditText) view.findViewById(R.id.changeBasketQuantity);
        mSparePartQuantity.setText(String.valueOf(quantity));
        mSparePartQuantity.setSelectAllOnFocus(true);

        Dialog dialog = initializeAlertDialog(view);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return dialog;
    }

    private AlertDialog initializeAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.AddSpPartToBasketLabel)
                .setView(view)
                .setNeutralButton("Delete", null)
                .setPositiveButton("Change", null)
                .setNegativeButton(R.string.CancelLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeBasketDialog.this.getDialog().cancel();
                    }
                });
        return initializeAlertDialogButtons(builder.create());
    }

    private AlertDialog initializeAlertDialogButtons(final AlertDialog alertDialog) {
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteClick.onClick(v);
                        dismissAllowingStateLoss();
                    }
                });
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mQuantityChangedListener.quantityChanged(Integer.valueOf(mSparePartQuantity.getText().toString()));
                        dismissAllowingStateLoss();
                    }
                });
            }
        });
        return alertDialog;
    }

    public ChangeBasketDialog setDeleteClick(View.OnClickListener deleteClick) {
        this.deleteClick = deleteClick;
        return this;
    }

    public ChangeBasketDialog setChangeClick(QuantityChangedListener listener) {
        this.mQuantityChangedListener = listener;
        return this;
    }
}
