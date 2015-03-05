import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Seky on 26. 2. 2015.
 */
public class XLS {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private File file;

    public XLS(String name, int rows, int cells) {
        this.file = new File(name);
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        for (int i = 0; i < rows; i++) {
            Row row = sheet.createRow(i);
            for (int a = 0; a < cells; a++) {
                row.createCell(a).setCellValue(0);
            }
        }
    }

    public void setCell(int x, int y, int value) {
        Row row = sheet.getRow(x);
        Cell cell = row.getCell(y);
        cell.setCellValue(value);
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
        XLS xls = new XLS("graph.xls", 3, 2);
        xls.setCell(0, 1, 4);
        xls.setCell(0, 1, 4);
        xls.setCell(0, 1, 4);
        xls.write();
    }

}
