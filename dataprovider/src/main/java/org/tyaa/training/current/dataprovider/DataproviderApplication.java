package org.tyaa.training.current.dataprovider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tyaa.training.current.dataprovider.console.Commands;
import org.tyaa.training.current.dataprovider.spreadsheet.ExcelFileReader;
import org.tyaa.training.current.dataprovider.network.RestApiSender;
import org.tyaa.training.current.dataprovider.spreadsheet.OdsFileReader;

@SpringBootApplication
public class DataproviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataproviderApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ExcelFileReader /* OdsFileReader */ reader, RestApiSender sender) {
		return args -> {
			switch (args[0]) {
				case Commands.WORD_STUDY -> sender.send(reader.readWordStudy(args[1]), "stub");
				case Commands.WORD_TEST -> reader.readWordTest(args[1]);
			}
			// reader.readWordStudy(args[1]);
		};
	}
}
