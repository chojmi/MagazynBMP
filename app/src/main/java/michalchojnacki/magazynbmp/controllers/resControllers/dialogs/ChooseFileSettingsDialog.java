package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.excelControllers.ExcelController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.UiOwner;
import michalchojnacki.magazynbmp.controllers.sharedPreferencesController.ChooseFileDialogSPref;
import michalchojnacki.magazynbmp.model.ChooseFileDialogModel;

public class ChooseFileSettingsDialog extends DialogFragment {

    private final String FILE_PATH = "filePath";

    private ChooseFileDialogSPref mChooseFileDialogSPref;
    private ChooseFileDialogModel mChooseFileDialogModel;
    private Context mContext;
    private FileChooserDialog mFileChooserDialog;
    private SparePartsDbController mSparePartsDbController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mFileChooserDialog = new FileChooserDialog(activity);
        ((DialogFragmentUpdater) mContext).updateDialogFragment(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choose_file, null);

        mChooseFileDialogModel = new ChooseFileDialogModel(view);
        mChooseFileDialogSPref = new ChooseFileDialogSPref(mChooseFileDialogModel);

        chooseFileButtonInitialization(view);
        readingSavedData(savedInstanceState);
        return initializeAlertDialog(view);
    }

    private void chooseFileButtonInitialization(View view) {
        Button chooseFileButton = (Button) view.findViewById(R.id.DialogChooseFileButton);
        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFileChooserDialog.chooseFile();
            }
        });
    }

    private void readingSavedData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mChooseFileDialogModel.setChosenFilePath(savedInstanceState.getString(FILE_PATH));
        } else {
            mChooseFileDialogSPref.readFromSPref(mContext);
        }
    }

    private AlertDialog initializeAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ChooseExcelFileLabel)
                .setView(view)
                .setPositiveButton(mContext.getString(R.string.OkLabel), null)
                .setNeutralButton(R.string.ResetToDefaultLabel, null)
                .setNegativeButton(R.string.CancelLabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChooseFileSettingsDialog.this.getDialog().cancel();
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
                        saveDataFromChosenFile();
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

    private void saveDataFromChosenFile() {
        if (!mChooseFileDialogModel.getChosenFileText().equals(mContext.getString(R.string.NoChosenFileLabel))) {
            Handler handler = createEndOfSavingOperationHandler();

            createExcelController().exportXlsToDb(handler,
                    mChooseFileDialogModel.getChosenFilePath(),
                    mChooseFileDialogModel.getSheetName());

        } else {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.NoChosenFileLabel)).showDialog(mContext);
        }
    }

    private void setDefaultValues() {
        mChooseFileDialogModel.setChosenFilePath(mContext.getString(R.string.NoChosenFileLabel));
        mChooseFileDialogModel.setDefaultValues(mContext);
        mChooseFileDialogSPref.saveToSPref(mContext);
    }

    private Handler createEndOfSavingOperationHandler() {
        return new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ((UiOwner) mContext).updateUi(R.id.StartActivityItemsQuantity);

                if (msg.what == Activity.RESULT_OK) {
                    mChooseFileDialogSPref.saveToSPref(mContext);
                }
                dismiss();
                ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                return true;
            }
        });
    }

    private ExcelController createExcelController() {
        ExcelController.Builder excelControllerBuilder = new ExcelController.Builder()
                .context(mContext)
                .sparePartsDbController(mSparePartsDbController)
                .partPrefix(mChooseFileDialogModel.getPartPrefix())
                .numberPlaceIndex(Integer.valueOf(mChooseFileDialogModel.getColumnNumber()))
                .typePlaceIndex(mChooseFileDialogModel.getTypeColumnIfChecked())
                .descriptionPlaceIndex(mChooseFileDialogModel.getDescriptionColumnIfChecked())
                .producerPlaceIndex(mChooseFileDialogModel.getProducerColumnIfChecked())
                .locationPlaceIndex(mChooseFileDialogModel.getLocationColumnIfChecked());

        return excelControllerBuilder.build();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FILE_PATH, mChooseFileDialogModel.getChosenFilePath());
    }

    public void setSparePartsDbController(SparePartsDbController sparePartsDbController) {
        mSparePartsDbController = sparePartsDbController;
    }

    public void fileChosen(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mChooseFileDialogModel.setChosenFilePath(mFileChooserDialog.getFileChosen(data));
        }
    }
}