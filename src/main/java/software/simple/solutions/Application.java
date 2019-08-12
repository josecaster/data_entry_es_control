package software.simple.solutions;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationHome;

import software.simple.solutions.framework.core.constants.Constants;

@SpringBootApplication
// (scanBasePackages = { "software.simple.solutions" })
// @EnableAutoConfiguration
// @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class,
// ManagementWebSecurityAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		// SpringApplication.run(Application.class, args);

		new SpringApplicationBuilder(Application.class)
				.properties("spring.config.name:application", "spring.config.location:classpath:/,file:./").build()
				.run(args);

		Constants.APPLICATION_HOME = new ApplicationHome(Application.class).getDir();
	}

}
