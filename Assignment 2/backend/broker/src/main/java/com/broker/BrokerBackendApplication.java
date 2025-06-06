package com.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokerBackendApplication {

	public static void main(String[] args) {
//		System.out.println(System.getProperty("java.home"));
//		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
		SpringApplication.run(BrokerBackendApplication.class, args);
	}

}
