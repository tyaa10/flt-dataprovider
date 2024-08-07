package org.tyaa.training.current.dataprovider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tyaa.training.current.dataprovider.console.Commands;
import org.tyaa.training.current.dataprovider.filesystem.spreadsheets.ExcelFileReader;
import org.tyaa.training.current.dataprovider.network.RestApiSender;

@SpringBootApplication
public class DataProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProviderApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ExcelFileReader /* OdsFileReader */ reader, RestApiSender sender) {
		return args -> {
			switch (args[0]) {
				// пример параметров командной строки:
				// word-study D:/tmp/flt-content/looks.xlsx
				case Commands.WORD_STUDY -> sender.send(
						reader.readWordStudy(args[1]),
						"http://localhost:8090/lang-trainer/api/import/lessons/word-study"
				);
				default -> System.err.println("Unknown command");
			}
		};
	}
}
