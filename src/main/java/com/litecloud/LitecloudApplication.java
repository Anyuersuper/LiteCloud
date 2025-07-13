package com.litecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitecloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitecloudApplication.class, args);
		System.out.print("LiteCloud start over!");
	}

}
