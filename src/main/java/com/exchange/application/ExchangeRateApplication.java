package com.exchange.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class ExchangeRateApplication {

	//private static Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public CommandLineRunner latestRate(ExchangeRateService exchangeRateService) {
//		return args -> {
//			logger.info("Latest Euro to Birr rate");
//			logger.info("1 Euro is: "+ exchangeRateService.rateResponse("TRY").getRates().get("TRY")+"TL");
//			logger.info("Latest Euro to Dollar rate");
//			logger.info("1 Euro is: "+ exchangeRateService.rateResponse("USD").getRates().get("USD")+"$");
//		};
//	}
}
