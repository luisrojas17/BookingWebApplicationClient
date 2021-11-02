package com.example.booking.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookingWebApplicationClient {

	private static final Logger logger = LoggerFactory.getLogger(BookingWebApplicationClient.class);
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	/**
	 * These interfaces get called just before run() once SpringApplication completes
	 */
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		
		return args -> {
			logger.info("Starting CLR application");
			
			ResponseEntity<List<Room>> rooms = 
					restTemplate.exchange("http://localhost:8000/api/rooms", HttpMethod.GET, null, 
							new ParameterizedTypeReference<List<Room>>() {
							});
			
			rooms.getBody().forEach(room -> {
				logger.info(room.toString());
			});
			
			logger.info("Finishing CLR application");
		};
		
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(BookingWebApplicationClient.class, args);
	}

}
