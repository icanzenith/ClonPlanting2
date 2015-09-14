package sugarcaneselection.thaib.org.clonplanting2.chart;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Jitpakorn on 5/24/15 AD.
 */
public class ExportToExcel {

    Context context;

    public ExportToExcel(Context context) {
        this.context = context;

    }

    private void CreateExcelFile(){
        final String fileName = "TodoList.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/javatechig.todo");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("ตารางแรก",0);

            //First Header
//            sheet.addCell(new Label());

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Debug",e.getMessage());
        }

    }
}
