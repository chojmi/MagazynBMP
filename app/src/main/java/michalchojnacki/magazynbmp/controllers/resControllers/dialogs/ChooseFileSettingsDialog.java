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
import android.view.WindowManager;
import android.widget.Button;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.excelControllers.ExcelController;
import michalchojnacki.magazynbmp.controllers.resControllers.activities.UiOwner;
import michalchojnacki.magazynbmp.controllers.sharedPreferencesController.ChooseFileDialogSPref;
import michalchojnacki.magazynbmp.model.ChooseFileDialogModel;

public class ChooseFileSettingsDialog extends DialogFragment {

    private final String FILE_PATH = "filePath";
    private final String OVERWRITE_ITEMS_DIALOG_VISIBLE = "overwriteItemsDialogVisible";
    private final String CLEAR_ITEMS_DIALOG_VISIBLE = "clearItemsDialogVisible";
    private final String CLEAR_DB = "clearDb";

    private ChooseFileDialogSPref mChooseFileDialogSPref;
    private ChooseFileDialogModel mChooseFileDialogModel;
    private Context mContext;
    private FileChooserDialog mFileChooserDialog;
    private SparePartsDbController mSparePartsDbController;

    private boolean clearDb = false;
    private QuestionDialog mOverwriteItemsDialog;
    private QuestionDialog mClearItemsDialog;

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

        Dialog dialog = initializeAlertDialog(view);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.getBoolean(OVERWRITE_ITEMS_DIALOG_VISIBLE)) {
            clearDb = savedInstanceState.getBoolean(CLEAR_DB);
            mOverwriteItemsDialog = getOverwriteItemsDialog(createEndOfSavingOperationHandler());
            mOverwriteItemsDialog.showDialog(mContext);
        } else if (savedInstanceState != null && savedInstanceState.getBoolean(CLEAR_ITEMS_DIALOG_VISIBLE))
            saveDataFromChosenFile();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FILE_PATH, mChooseFileDialogModel.getChosenFilePath());

        if (mOverwriteItemsDialog != null && mOverwriteItemsDialog.getActivity() == mContext) {
            outState.putBoolean(OVERWRITE_ITEMS_DIALOG_VISIBLE, true);
            outState.putBoolean(CLEAR_ITEMS_DIALOG_VISIBLE, false);
            outState.putBoolean(CLEAR_DB, clearDb);
        } else if (mClearItemsDialog != null && mClearItemsDialog.getActivity() == mContext) {
            outState.putBoolean(CLEAR_ITEMS_DIALOG_VISIBLE, true);
            outState.putBoolean(OVERWRITE_ITEMS_DIALOG_VISIBLE, false);
        } else {
            outState.putBoolean(CLEAR_ITEMS_DIALOG_VISIBLE, false);
            outState.putBoolean(OVERWRITE_ITEMS_DIALOG_VISIBLE, false);
        }
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
            final Handler handler = createEndOfSavingOperationHandler();
            mOverwriteItemsDialog = getOverwriteItemsDialog(handler);
            mClearItemsDialog = getClearItemsDialog();
            mClearItemsDialog.showDialog(mContext);
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
                mClearItemsDialog = null;
                mOverwriteItemsDialog = null;
                dismiss();
                ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                return true;
            }
        });
    }

    private QuestionDialog getOverwriteItemsDialog(final Handler handler) {
        return QuestionDialog.newInstance(mContext.getString(R.string.WarningLabel), mContext.getString(R.string.OverwriteOldSparePartLabel))
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (clearDb) {
                            ((ClearDatabaseDialog.ClearDatabaseListener) mContext).clearDatabase();
                        }
                        createExcelController(true).exportXlsToDb(handler,
                                mChooseFileDialogModel.getChosenFilePath(),
                                mChooseFileDialogModel.getSheetName());
                    }
                })
                .setNegativeClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (clearDb) {
                            ((ClearDatabaseDialog.ClearDatabaseListener) mContext).clearDatabase();
                        }
                        createExcelController(false).exportXlsToDb(handler,
                                mChooseFileDialogModel.getChosenFilePath(),
                                mChooseFileDialogModel.getSheetName());
                    }
                });
    }

    private QuestionDialog getClearItemsDialog() {
        return QuestionDialog.newInstance(mContext.getString(R.string.WarningLabel), mContext.getString(R.string.DelAllSparePartsLabel))
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDb = true;
                        mOverwriteItemsDialog.showDialog(mContext);
                    }
                }).setNegativeClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDb = false;
                        mOverwriteItemsDialog.showDialog(mContext);
                    }
                });
    }

    private ExcelController createExcelController(boolean overwriteOldParts) {
        ExcelController.Builder excelControllerBuilder = new ExcelController.Builder()
                .context(mContext)
                .sparePartsDbController(mSparePartsDbController)
                .partPrefix(mChooseFileDialogModel.getPartPrefix())
                .numberPlaceIndex(Integer.valueOf(mChooseFileDialogModel.getColumnNumber()))
                .typePlaceIndex(mChooseFileDialogModel.getTypeColumnIfChecked())
                .descriptionPlaceIndex(mChooseFileDialogModel.getDescriptionColumnIfChecked())
                .producerPlaceIndex(mChooseFileDialogModel.getProducerColumnIfChecked())
                .locationPlaceIndex(mChooseFileDialogModel.getLocationColumnIfChecked())
                .supplierPlaceIndex(mChooseFileDialogModel.getSupplierColumnIfChecked())
                .overwriteOldPart(overwriteOldParts);

        return excelControllerBuilder.build();

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