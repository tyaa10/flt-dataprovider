package org.tyaa.training.current.dataprovider.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFileReader {
    public static void read() throws IOException {
        final String path = "/home/yurii/Documents/flt/looks.xlsx";
        FileInputStream file = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(1);
        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING: data.get(i).add(cell.getStringCellValue()); break;
                    case NUMERIC: data.get(i).add(String.valueOf(cell.getNumericCellValue())); break;
                    // case BOOLEAN: ... break;
                    // case FORMULA: ... break;
                    default: data.get(i).add(" ");
                }
            }
            i++;
        }
        data.forEach((index, strings) -> {
            System.out.println("Row #1:");
            strings.forEach(s -> {
                System.out.printf("%20s", s);
            });
        });
    }
}
