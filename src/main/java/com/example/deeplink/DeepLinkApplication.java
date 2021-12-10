package com.example.deeplink;

import com.example.deeplink.util.MaruRSA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;

@SpringBootApplication
public class DeepLinkApplication {


	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(DeepLinkApplication.class, args);
	}

	@Bean
	MaruRSA maruRSA() throws Exception {
		return new MaruRSA();
	}

}
