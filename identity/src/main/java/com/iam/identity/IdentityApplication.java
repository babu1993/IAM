package com.iam.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdentityApplication {

	public static void main(String[] args) {
		System.out.println("DataBase Host:" + System.getenv("DB_PASSWORD"));
		SpringApplication.run(IdentityApplication.class, args);
	}

}
