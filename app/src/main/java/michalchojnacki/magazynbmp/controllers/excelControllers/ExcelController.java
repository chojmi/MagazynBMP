package michalchojnacki.magazynbmp.controllers.excelControllers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import michalchojnacki.magazynbmp.R;
import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.ErrorDialog;
import michalchojnacki.magazynbmp.controllers.resControllers.dialogs.LoadingDbDialog;
import michalchojnacki.magazynbmp.model.SparePart;

public class ExcelController extends ExcelControllerModel {

    private final Context mContext;
    private final SparePartsDbController mSparePartsDbController;
    private FileInputStream file;
    private LoadingDbDialog mLoadingDbDialog;

    private ExcelController(Builder builder) {
        mSparePartsDbController = builder.mSparePartsDbController;
        mContext = builder.mContext;
        mNumberPlaceIndex = builder.mNumberPlaceIndex;
        mPartPrefix = builder.mPartPrefix;
        mDescriptionPlaceIndex = builder.mDescriptionPlaceIndex;
        mTypePlaceIndex = builder.mTypePlaceIndex;
        mLocationPlaceIndex = builder.mLocationPlaceIndex;
        mProducerPlaceIndex = builder.mProducerPlaceIndex;
        mLoadingDbDialog = new LoadingDbDialog();
    }

    public void exportXlsToDb(final Handler handler, final String path, final String sheetName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLoadingDbDialog.start(mContext);
                HSSFSheet sheet = getSheetFromXls(path, sheetName);
                saveSheetInDb(sheet, handler);
            }
        }).start();
        ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    private HSSFSheet getSheetFromXls(String path, String sheetName) {

        try {
            file = new FileInputStream(new File(path));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            return workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.NoFileFoundLabel)).showDialog(mContext);
            return null;
        } catch (IOException e) {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.NoFileFoundLabel)).showDialog(mContext);
            return null;
        }
    }

    private void saveSheetInDb(Sheet sheet, final Handler handler) {
        if (sheet != null) {
            for (Row row : sheet) {
                saveNextRowInDb(row);
            }
            closeFile();
            handler.sendEmptyMessage(Activity.RESULT_OK);
        } else {
            ErrorDialog.newInstance(mContext.getString(R.string.ErrorLabel),
                    mContext.getString(R.string.NoSheetFound)).showDialog(mContext);
        }
        mLoadingDbDialog.stop();
    }

    private void saveNextRowInDb(Row row) {
        if (row.getCell(mNumberPlaceIndex) != null) {
            row.getCell(mNumberPlaceIndex).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(mNumberPlaceIndex).getStringCellValue().startsWith(mPartPrefix)) {
                if (isRowSavedWithSuccess(row)) {
                    mLoadingDbDialog.nextValueSaved();
                }
            }
        }
    }

    private void closeFile() {
        try {
            file.close();
        } catch (IOException e) {
        }
    }

    private boolean isRowSavedWithSuccess(Row row) {
        SparePart sparePart = new SparePart.Builder()
                .location(saveCell(row, mLocationPlaceIndex))
                .description(saveCell(row, mDescriptionPlaceIndex))
                .number(saveCell(row, mNumberPlaceIndex))
                .type(saveCell(row, mTypePlaceIndex))
                .producer(saveCell(row, mProducerPlaceIndex))
                .build();

        return mSparePartsDbController.saveSparePart(sparePart);

    }

    private String saveCell(Row row, int placeIndex) {

        if (row != null && row.getCell(placeIndex) != null) {
            row.getCell(placeIndex).setCellType(Cell.CELL_TYPE_STRING);
            return row.getCell(placeIndex).getStringCellValue().trim();
        }

        return mContext.getString(R.string.NoDataLabel);

    }

    public static class Builder extends ExcelControllerModel {

        public ExcelController build() {
            return new ExcelController(this);
        }

        public Builder context(Context context) {
            mContext = context;
            mPartPrefix = context.getString(R.string.DefaultPartPrefix);
            return this;
        }

        public Builder sparePartsDbController(SparePartsDbController sparePartsDbController) {
            mSparePartsDbController = sparePartsDbController;
            return this;
        }

        public Builder partPrefix(String partPrefix) {
            mPartPrefix = partPrefix;
            return this;
        }

        public Builder numberPlaceIndex(int numberIndex) {
            mNumberPlaceIndex = numberIndex;
            return this;
        }

        public Builder descriptionPlaceIndex(String descriptionPlaceIndex) {
            if (descriptionPlaceIndex != null)
                mDescriptionPlaceIndex = Integer.valueOf(descriptionPlaceIndex);
            return this;
        }

        public Builder typePlaceIndex(String typePlaceIndex) {
            if (typePlaceIndex != null) {
                mTypePlaceIndex = Integer.valueOf(typePlaceIndex);
            }
            return this;
        }

        public Builder locationPlaceIndex(String locationPlaceIndex) {
            if (locationPlaceIndex != null) {
                mLocationPlaceIndex = Integer.valueOf(locationPlaceIndex);
            }
            return this;
        }

        public Builder producerPlaceIndex(String producerPlaceIndex) {
            if (producerPlaceIndex != null) {
                mProducerPlaceIndex = Integer.valueOf(producerPlaceIndex);
            }
            return this;
        }
    }
}

class ExcelControllerModel {

    Context mContext;
    SparePartsDbController mSparePartsDbController;

    int mNumberPlaceIndex = 2;
    int mDescriptionPlaceIndex = -1;
    int mTypePlaceIndex = -1;
    int mLocationPlaceIndex = -1;
    int mProducerPlaceIndex = -1;

    String mPartPrefix;
}
