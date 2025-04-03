package com.bookease.bookease;

import jakarta.transaction.Transactional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Transactional
public class BookeaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookeaseApplication.class, args);
	}

}
