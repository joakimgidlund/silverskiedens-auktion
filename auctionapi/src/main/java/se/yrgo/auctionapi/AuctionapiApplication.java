package se.yrgo.auctionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class AuctionapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionapiApplication.class, args);
	}

}
