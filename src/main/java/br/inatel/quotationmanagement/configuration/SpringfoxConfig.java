package br.inatel.quotationmanagement.configuration;

import javax.management.Notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.cj.protocol.Message;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxConfig {
	
	@Bean
	public Docket QuoteManagerAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.inatel.quotationmanagement"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Message.class, Notification.class)
				.apiInfo(new ApiInfoBuilder()
						.title("Stock Manager")
						.description("Application that manages the stock quotes of registered stocks in a external API.")
						.build());
	}

}
