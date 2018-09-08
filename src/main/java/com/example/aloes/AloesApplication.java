package com.example.aloes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AloesApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AloesApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {



	}
}
