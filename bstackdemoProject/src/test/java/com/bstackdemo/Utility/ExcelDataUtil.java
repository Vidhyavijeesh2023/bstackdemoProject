package com.bstackdemo.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelDataUtil {

    private static XSSFWorkbook wb;

    static {
        try (FileInputStream fs = new FileInputStream(
                new File(System.getProperty("user.dir") + "//TestData//PurchasePageData.xlsx"))) {
            wb = new XSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] getSheetData(String sheetname) {
        int rows = wb.getSheet(sheetname).getPhysicalNumberOfRows();
        int cols = wb.getSheet(sheetname).getRow(0).getPhysicalNumberOfCells();

        String[][] data = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                XSSFCell cellvalue = wb.getSheet(sheetname).getRow(i).getCell(j);
                if (cellvalue == null) {
                    data[i][j] = "";
                } else {
                    switch (cellvalue.getCellType()) {
                        case STRING:
                            data[i][j] = cellvalue.getStringCellValue();
                            break;
                        case NUMERIC:
                            // Convert numeric to string (no decimals if integer)
                            double d = cellvalue.getNumericCellValue();
                            if (d == Math.floor(d)) {
                                data[i][j] = String.valueOf((long) d);
                            } else {
                                data[i][j] = String.valueOf(d);
                            }
                            break;
                        case BOOLEAN:
                            data[i][j] = String.valueOf(cellvalue.getBooleanCellValue());
                            break;
                        default:
                            data[i][j] = "";
                    }
                }
            }
        }
        return data;
    }
}
