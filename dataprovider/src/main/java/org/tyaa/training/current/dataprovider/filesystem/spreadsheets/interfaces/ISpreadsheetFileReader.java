package org.tyaa.training.current.dataprovider.filesystem.spreadsheets.interfaces;

import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;

import java.io.IOException;
import java.util.List;

public interface ISpreadsheetFileReader {
    List<IExportModel> readWordStudy(String filePath) throws IOException;
}
