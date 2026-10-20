package dev.mednikov.accounting.gateway;

import dev.mednikov.accounting.gateway.config.RouteLocationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RouteLocationProperties.class})
public class AccountingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingGatewayApplication.class, args);
	}

}
