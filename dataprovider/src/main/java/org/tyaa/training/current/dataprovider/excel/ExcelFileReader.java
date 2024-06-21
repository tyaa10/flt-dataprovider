package org.tyaa.training.current.dataprovider.excel;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.tyaa.training.current.dataprovider.models.WordStudyExportModel;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelFileReader {
    /**
     * Прочесть файл xlsx с данными для изучения слов
     * @param filePath путь к файлу в файловой системе, например, /home/yurii/Documents/flt/looks.xlsx, D:/tmp/looks.xlsx и так далее
     * */
    public List<IExportModel> readWordStudy(String filePath) throws IOException {

        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(1);

        Map<String, Integer> headerMap = new HashMap<>();
        int headerCellIndex = 0;

        for (Cell headerCell : sheet.getRow(0)) {
            String headerCellValue = headerCell.getStringCellValue();
            if (!headerCellValue.isBlank()) {
                headerMap.put(headerCell.getStringCellValue(), headerCellIndex);
            }
            headerCellIndex++;
        }

        LinkedHashMap<Integer, List<String>> data = new LinkedHashMap<>();
        int rowCellIndex = 0;
        for (Row row : sheet) {
            if(rowCellIndex == 0) {
                rowCellIndex++;
                continue;
            }
            data.put(rowCellIndex, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING -> data.get(rowCellIndex).add(cell.getStringCellValue());
                    case NUMERIC -> data.get(rowCellIndex).add(String.valueOf(cell.getNumericCellValue()));
                    // case BOOLEAN: ... break;
                    // case FORMULA: ... break;
                    // default: data.get(i).add(" ");
                }
            }
            rowCellIndex++;
        }

        XSSFDrawing patriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
        List<XSSFShape> shapes = patriarch.getShapes();
        List<byte[]> picturesData =
            shapes.stream()
                    .filter(Picture.class::isInstance)
                    .map(s -> (Picture) s)
                    .map(picture -> picture.getPictureData().getData())
                    .toList();

        System.out.println(headerMap);

        System.out.println(data);

        final List<IExportModel> words = new ArrayList<>();
        data.forEach((index, strings) -> {
            if (!strings.isEmpty() && !strings.get(0).isBlank()) {
                /* System.out.println(index);
                System.out.println(strings.get(headerMap.get("Уровень")));
                System.out.println(strings.get(headerMap.get("урок")));
                System.out.println(Base64.getEncoder().encodeToString(picturesData.get(index - 1))); */
                words.add(WordStudyExportModel.builder()
                            .levelName(strings.get(headerMap.get("Уровень")))
                            .lessonName(strings.get(headerMap.get("урок")))
                            .image(Base64.getEncoder().encodeToString(picturesData.get(index - 1)))
                            .build());
                // System.out.println();
            }
        });
        return words;
    }

    /**
     * Прочесть файл xlsx с данными для проверки знания слов
     * @param filePath путь к файлу в файловой системе
     * */
    public void readWordTest(String filePath) {
        throw new NotImplementedException("Not implemented yet");
    }
}
