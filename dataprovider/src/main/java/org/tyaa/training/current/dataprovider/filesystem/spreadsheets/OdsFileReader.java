package org.tyaa.training.current.dataprovider.filesystem.spreadsheets;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.springframework.stereotype.Service;
import org.tyaa.training.current.dataprovider.filesystem.spreadsheets.interfaces.ISpreadsheetFileReader;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OdsFileReader implements ISpreadsheetFileReader {
    @Override
    public List<IExportModel> readWordStudy(String filePath) throws IOException {
        Workbook workbook = new Workbook();
        workbook.loadFromFile(filePath);
        Worksheet worksheet = workbook.getWorksheets().get("уроки");
        AtomicInteger rowIndex = new AtomicInteger(0);
        Arrays.stream(worksheet.getRows()).forEach(cellRange -> {
            System.out.println(rowIndex.get());
            // cellRange.
            /* cellRange.forEach(o -> {
                System.out.println(o.getClass().getName());
            }); */

            rowIndex.getAndIncrement();
        });
        return null;
    }
}
