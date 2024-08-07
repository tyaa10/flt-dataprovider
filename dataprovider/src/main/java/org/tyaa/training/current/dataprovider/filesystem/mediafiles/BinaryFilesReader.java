package org.tyaa.training.current.dataprovider.filesystem.mediafiles;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class BinaryFilesReader {

    public String read(String filePath) throws IOException {
        return Base64.getEncoder().encodeToString(
                Files.readAllBytes(Paths.get(filePath))
        );
    }
}
