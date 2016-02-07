package michalchojnacki.magazynbmp.controllers.resControllers.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nononsenseapps.filepicker.FilePickerActivity;

import java.net.URISyntaxException;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.utils.FileUtils;

public class FileChooserDialog {

    public static final int FILE_SELECT_CODE = 0;
    private final Context mContext;

    public FileChooserDialog(Context context) {
        mContext = context;
    }

    public void chooseFile() {
        Intent i = new Intent(mContext, FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        ((Activity) mContext).startActivityForResult(i, FileChooserDialog.FILE_SELECT_CODE);
    }

    public String getFileChosen(Intent data) {

        Uri uri = data.getData();
        String selectedPath = mContext.getString(R.string.NoChosenFileLabel);
        try {
            selectedPath = isXlsFile(uri);
        } catch (URISyntaxException e) {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.YouNeedChooseXlsFile)).showDialog(mContext);
        } catch (IllegalArgumentException e) {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.YouNeedChooseXlsFile)).showDialog(mContext);
        }

        return selectedPath;
    }

    private String isXlsFile(Uri uri) throws URISyntaxException, IllegalArgumentException {
        String selectedPath = null;
        if (uri.getLastPathSegment().endsWith(mContext.getString(R.string.XlsExtension))) {
            selectedPath = FileUtils.getPath(mContext, uri);
        } else {
            throw new IllegalArgumentException("Should be an .xls file!");
        }
        return selectedPath;
    }
}
