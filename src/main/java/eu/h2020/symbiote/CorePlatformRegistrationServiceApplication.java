package eu.h2020.symbiote;

import eu.h2020.symbiote.platform.Platform;
import eu.h2020.symbiote.platform.PlatformStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class CorePlatformRegistrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorePlatformRegistrationServiceApplication.class, args);
	}

}

@RepositoryRestResource
interface PlatformRepository extends JpaRepository<Platform,Long> {

}

@RestController
class RegistrationController {

	private static final String DIRECTORY = "/corePlatformTriplestore";

	public RegistrationController() {
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes = "application/json")
    String register(@RequestBody Platform platform ) {
		String platformUri = PlatformStorage.getInstance(DIRECTORY).store(platform);
		return platformUri;
	}
//    String register() {
//        return "Platform registered";
//    }
}


@RestController
@RefreshScope
class MessageRestController {
	private final String value;

	@Autowired
	public MessageRestController(
			@Value("${message}") String value) {
		this.value = value;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/message")
	String read() {
		return this.value;
	}
}