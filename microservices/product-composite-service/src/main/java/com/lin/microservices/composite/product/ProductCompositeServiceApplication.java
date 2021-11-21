package com.lin.microservices.composite.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.emptyList;
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SpringBootApplication
@ComponentScan("com.lin")
public class ProductCompositeServiceApplication {
// do we really want to put api information in application properties file??? it will mess the other important properties	
//    @Value("${api.common.version}")           String apiVersion;
//    @Value("${api.common.title}")             String apiTitle;
//    @Value("${api.common.description}")       String apiDescription;
//    @Value("${api.common.termsOfServiceUrl}") String apiTermsOfServiceUrl;
//    @Value("${api.common.license}")           String apiLicense;
//    @Value("${api.common.licenseUrl}")        String apiLicenseUrl;
//    @Value("${api.common.contact.name}")      String apiContactName;
//    @Value("${api.common.contact.url}")       String apiContactUrl;
//    @Value("${api.common.contact.email}")     String apiContactEmail;

	/**
	 * Will exposed on $HOST:$PORT/swagger-ui/ and /v2/api-docs
	 *
	 * @return
	 */
	@Bean
	public Docket apiDocumentation() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(basePackage("com.lin")) // RequestHandlerSelectors.any() 
			.paths(PathSelectors.any())
			.build();
//              .globalResponseMessage(GET, emptyList())
//				.apiInfo(new ApiInfo(
//                    apiTitle,
//                    apiDescription,
//                    apiVersion,
//                    apiTermsOfServiceUrl,
//                    new Contact(apiContactName, apiContactUrl, apiContactEmail),
//                    apiLicense,
//                    apiLicenseUrl,
//                    emptyList()
//                ));
    }

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductCompositeServiceApplication.class, args);
	}
}
