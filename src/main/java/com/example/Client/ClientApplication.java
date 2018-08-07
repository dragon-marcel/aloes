package com.example.Client;

import com.example.Client.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class ClientApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {



	}
}
