package michalchojnacki.magazynbmp.controllers.excelControllers;

import android.os.Environment;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import michalchojnacki.magazynbmp.controllers.dbControllers.SparePartsDbController;
import michalchojnacki.magazynbmp.model.SparePart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class ExcelControllerTest {

    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/test.xls");
    private String sheetName = "Automatyka";
    private ExcelControllerModel mExcelControllerModel = new ExcelControllerModel();

    @Before
    public void initExcelFile() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        row.createCell(mExcelControllerModel.mNumberPlaceIndex).setCellValue("YA2020");
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
    }

    @Test
    public void isItemRead() {
        SparePartsDbController sparePartsDbController = new SparePartsDbController(InstrumentationRegistry.getTargetContext());
        ExcelController excelController = new ExcelController.Builder()
                .context(InstrumentationRegistry.getTargetContext())
                .numberPlaceIndex(mExcelControllerModel.mNumberPlaceIndex)
                .sparePartsDbController(sparePartsDbController)
                .build();

        Handler handler = new Handler(InstrumentationRegistry.getTargetContext().getMainLooper());

        excelController.exportXlsToDb(handler, file.getAbsolutePath(), sheetName);

        SparePart[] spareParts = sparePartsDbController.findSparePart("");

        assertFalse(spareParts.length != 1);
        assertThat(spareParts[0].getNumber(), equalTo("YA2020"));

    }

    @After
    public void deleteFile() {
        file.delete();
    }
}