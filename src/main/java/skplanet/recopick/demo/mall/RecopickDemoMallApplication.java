package skplanet.recopick.demo.mall;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(
		basePackageClasses = {Jsr310JpaConverters.class},
		basePackages = {"skplanet.recopick.demo.mall"}
)
public class RecopickDemoMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecopickDemoMallApplication.class, args);
	}

	@Bean(name = "objMapper")
	public ObjectMapper caseInsensitiveObjectMapper() {
		return new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}
}
