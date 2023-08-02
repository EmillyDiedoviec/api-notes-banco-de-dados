package br.com.apiNotes.apinotes;

import br.com.apiNotes.apinotes.dataBase.DataBase;
import br.com.apiNotes.apinotes.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiNotesApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(ApiNotesApplication.class, args);
	}

}
