package org.tyaa.training.current.dataprovider.spreadsheet;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.tyaa.training.current.dataprovider.models.WordStudyExportModel;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;
import org.tyaa.training.current.dataprovider.spreadsheet.interfaces.ISpreadsheetFileReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelFileReader implements ISpreadsheetFileReader {
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
                headerMap.put(headerCellValue, headerCellIndex);
            }
            headerCellIndex++;
        }

        // Чтение текстовых и числовых данных строк в словарь
        LinkedHashMap<Integer, Map<Integer, Object>> simpleData = new LinkedHashMap<>();
        int rowCellIndex = 0;
        for (Row row : sheet) {
            if(rowCellIndex == 0) {
                rowCellIndex++;
                continue;
            }
            simpleData.put(rowCellIndex, new LinkedHashMap<>());
            for (Cell cell : row) {
                System.out.println(cell.getCellType());
                switch (cell.getCellType()) {
                    case STRING -> simpleData.get(rowCellIndex).put(cell.getColumnIndex(), cell.getStringCellValue().isBlank() ? "-" : cell.getStringCellValue());
                    case NUMERIC -> simpleData.get(rowCellIndex).put(cell.getColumnIndex(), (int) Math.round(cell.getNumericCellValue()));
                    // case BLANK -> simpleData.get(rowCellIndex).add("-");
                    // case BOOLEAN: ... break;
                    // case FORMULA: ... break;
                    // default -> simpleData.get(rowCellIndex).put(cell.getColumnIndex(), "-");
                }
            }
            System.out.println();
            rowCellIndex++;
        }

        // Чтение двоичных данных (изображений и аудио) листа в список
        XSSFDrawing patriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
        // patriarch.getShapes().forEach(xssfShape -> System.out.println(xssfShape));
        // patriarch.getCTDrawing().getOneCellAnchorList().forEach(ctOneCellAnchor -> System.out.println(ctOneCellAnchor));
        List<XSSFShape> shapes = patriarch.getShapes();
        // shapes.stream().forEach(xssfShape -> xssfShape.getShapeName());
        List<Picture> pictures =
            shapes.stream()
                .filter(Picture.class::isInstance)
                .map(xssfShape -> (Picture)xssfShape)
                // .filter(xSSFObjectData -> xSSFObjectData.getContentType().equals("application/vnd.openxmlformats-officedocument.oleObject"))
                .toList();
                /*.forEach(object -> {
                    if (object.getAnchor() instanceof ClientAnchor anc) {
                        System.out.println("Row: " + anc.getRow1() + " Column " + anc.getCol1());
                        // System.out.println(object.getDirectory().getEntries().next());
                            // System.out.println(new String(object.getObjectData()));
                            // System.out.println(Base64.getEncoder().encodeToString(object.getObjectData()));
                            // DirectoryNode dn = (DirectoryNode)object.getDirectory();
                            // Iterator<Entry> ab = dn.getEntries();
                            // InputStream is;
                            // StringBuilder sb = new StringBuilder();
                            // ab.next();
                            // ab.next();
                            //byte[] fileBytes = new byte[16777216];
                            // ByteBuffer byteBuffer = ByteBuffer.allocate(16777216);
                            // Entry entry = ab.next();
                            // System.out.println(entry.isDocumentEntry());
                            // entry = ab.next();
                            // is = dn.createDocumentInputStream(entry);
                            // System.out.println(sb.toString());
                            // System.out.println(Base64.getEncoder().encodeToString(bytes));
                            // System.out.println(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
                            // System.out.println(entry.isDocumentEntry());
                            // System.out.println(Base64.getEncoder().encodeToString(dn.createDocumentInputStream(entry).readAllBytes()));
                    }
                }); */

        // shapes.stream().filter(org.apache.poi.ss.usermodel.)

        System.out.println(headerMap);

        System.out.println(simpleData);

        final List<IExportModel> words = new ArrayList<>();
        simpleData.forEach((index, values) -> {
            if (!values.isEmpty() && !values.get(0).toString().isBlank()) {
                //try {
                System.out.println("NO: " + values.get(headerMap.get("номер объекта")));
                    words.add(WordStudyExportModel.builder()
                            .lessonName((String) values.get(headerMap.get("урок")))
                            .nativeLanguageName((String) values.get(headerMap.get("родной язык")))
                            .learningLanguageName((String) values.get(headerMap.get("изучаемый язык")))
                            .levelName((String) values.get(headerMap.get("уровень")))
                            .sequenceNumber((Integer) values.get(headerMap.get("номер объекта")))
                            .word((String) values.get(headerMap.get("слово")))
                            .translation((String) values.get(headerMap.get("перевод")))
                            // .pronunciationAudio(strings.get(headerMap.get("аудио")))
                            .image(Base64.getEncoder().encodeToString(pictures.stream().filter(object -> {
                                if (object.getAnchor() instanceof ClientAnchor anc) {
                                    // System.out.println("Row: " + anc.getRow1() + " Column " + anc.getCol1());
                                    return (anc.getRow1() == index && anc.getCol1() == headerMap.get("картинка"));
                                } else {
                                    return false;
                                }
                            }).findFirst().get().getPictureData().getData()))
                            .pronunciationAudio("STUB")
                            .build());
                /*} catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            }
        });
        workbook.close();
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
