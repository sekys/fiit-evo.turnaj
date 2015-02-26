import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * Created by Seky on 26. 2. 2015.
 */
public class XLS {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private int rows;
    private File file;

    public XLS(String name) {
        this.file = new File(name);
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        rows = 0;
        Iterator<Row> rowIte =  sheet.iterator();
        while(rowIte.hasNext()){
            rowIte.next();
            rowIte.remove();
        }
    }

    public void addRow(Integer x, Double y) {
        synchronized (this) {
            Row row = sheet.createRow(rows);
            row.createCell(0).setCellValue(x);
            row.createCell(1).setCellValue(y);
            rows++;
        }
    }

    public void write() {
        try {
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        XLS xls = new XLS("graf.xls");
        xls.addRow(0, 4.5);
        xls.addRow(0, 4.5);
        xls.addRow(0, 4.5);
        xls.write();
    }

}
