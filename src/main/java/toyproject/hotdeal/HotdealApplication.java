package toyproject.hotdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:/aws.properties")
public class HotdealApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotdealApplication.class, args);
	}

}
