package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;

import java.util.Arrays;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.SparePartViewer;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.SparePartsViewer;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.StartActivity;
import michalchojnacki.magazynbmp.controllers.sharedPreferencesController.SearchDialogSPref;
import michalchojnacki.magazynbmp.model.SearchDialogModel;
import michalchojnacki.magazynbmp.model.SparePart;

public class SearchDialog extends DialogFragment {

    private Context mContext;
    private SearchDialogModel mSearchDialogModel;
    private SparePartsDbController mSparePartsDbController;
    private SearchDialogSPref mSearchDialogSPref;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        ((DialogFragmentUpdater) mContext).updateDialogFragment(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_part, null);

        mSearchDialogModel = new SearchDialogModel(view);
        mSearchDialogSPref = new SearchDialogSPref(mSearchDialogModel);

        readingSavedData(savedInstanceState);
        Dialog dialog = initializeAlertDialog(view);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return dialog;
    }

    private void readingSavedData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mSearchDialogSPref.readFromSPref(mContext);
        }
    }

    private AlertDialog initializeAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.SearchLabel)
                .setView(view)
                .setPositiveButton(R.string.OkLabel, null)
                .setNeutralButton(R.string.ClearLabel, null)
                .setNegativeButton(R.string.CancelLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SearchDialog.this.getDialog().cancel();
                    }
                });
        return initializeAlertDialogButtons(builder.create());
    }

    private AlertDialog initializeAlertDialogButtons(final AlertDialog alertDialog) {
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SparePart[] spareParts = findSpareParts();
                        startActivityIfSthFound(spareParts);
                    }
                });
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDefaultValues();
                    }
                });
            }
        });
        return alertDialog;
    }

    private SparePart[] findSpareParts() {
        SparePart[] spareParts;
        SparePart searchedSparePart = createSearchedSparePart();
        spareParts = mSparePartsDbController.findSparePart(searchedSparePart);
        return spareParts;
    }

    private void startActivityIfSthFound(SparePart[] spareParts) {
        if (spareParts == null || spareParts.length < 1) {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.NoSparePartFoundLabel)).showDialog(mContext);
        } else {
            startProperActivity(spareParts);
        }
    }

    private void setDefaultValues() {
        mSearchDialogModel.setDefaultValues();
        mSearchDialogSPref.saveToSPref(mContext);
    }

    private SparePart createSearchedSparePart() {
        return new SparePart.Builder()
                .number(mSearchDialogModel.getPartNumberIfChecked())
                .description(mSearchDialogModel.getPartDescriptionIfChecked())
                .type(mSearchDialogModel.getPartTypeIfChecked())
                .producer(mSearchDialogModel.getPartProducerIfChecked())
                .supplier(mSearchDialogModel.getPartSupplierIfChecked())
                .build();
    }

    private void startProperActivity(SparePart[] spareParts) {
        Intent intent = null;
        if (spareParts.length == 1) {
            intent = new Intent(mContext, SparePartViewer.class);
            intent.putExtra(SparePartViewer.SPARE_PART, spareParts[0]);
        } else if (spareParts.length > 1 && spareParts.length <= 1000) {
            intent = new Intent(mContext, SparePartsViewer.class);
            intent.putExtra(SparePartsViewer.SPARE_PARTS, spareParts);
        } else if (spareParts.length > 1000) {
            intent = new Intent(mContext, SparePartsViewer.class);
            intent.putExtra(SparePartsViewer.SPARE_PARTS, Arrays.copyOf(spareParts, 1000));
        }
        intent.putExtra(SparePartViewer.BASKET_CONTROLLER, ((StartActivity) mContext).getBasketController());
        mSearchDialogSPref.saveToSPref(mContext);
        ((StartActivity) mContext).startActivityForResult(intent, SparePartViewer.SPARE_PARTS_VIEWER_STOPPED);
    }

    public void setSparePartsDbController(SparePartsDbController sparePartsDbController) {
        mSparePartsDbController = sparePartsDbController;
    }
}
