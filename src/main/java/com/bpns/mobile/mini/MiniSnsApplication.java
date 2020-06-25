package com.bpns.mobile.mini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({	
	FileUploadProperties.class
}) 
public class MiniSnsApplication {
	public static void main(String[] args) {
//		SpringApplication.run(MiniSnsApplication.class, args);
		
		SpringApplication application = new SpringApplication(MiniSnsApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

}
