package eu.h2020.symbiote;

import eu.h2020.symbiote.messaging.RabbitMessager;
import eu.h2020.symbiote.platform.Platform;
import eu.h2020.symbiote.platform.PlatformStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class CorePlatformRegistrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorePlatformRegistrationServiceApplication.class, args);
	}

}

@RestController
class RegistrationController {

	private static Log log = LogFactory.getLog(RegistrationController.class);

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