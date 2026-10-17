package dev.mednikov.accounting.gateway;

import dev.mednikov.accounting.gateway.config.RouteLocation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RouteLocation.class})
public class AccountingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingGatewayApplication.class, args);
	}

}
