package org.tyaa.training.current.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tyaa.training.current.dataprovider.excel.ExcelFileReader;

import java.io.IOException;

@SpringBootApplication
public class DataproviderApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DataproviderApplication.class, args);
		ExcelFileReader.read();
	}

}
