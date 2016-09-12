package eu.h2020.symbiote;

import eu.h2020.symbiote.messaging.PlatformRegistrationPublisher;
import eu.h2020.symbiote.repository.InformationModel;
import eu.h2020.symbiote.repository.Platform;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableDiscoveryClient
@SpringBootApplication
public class CorePlatformRegistrationServiceApplication {

	public static Log log = LogFactory.getLog(CorePlatformRegistrationServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CorePlatformRegistrationServiceApplication.class, args);
	}

}

@RepositoryRestResource(collectionResourceRel = "platform", path = "platform")
interface PlatformRepository extends MongoRepository<Platform, String> {


}

@RepositoryRestResource(collectionResourceRel = "informationmodel", path = "informationmodel")
interface InformationModelRepository extends MongoRepository<InformationModel, String> {

}

@RepositoryRestController
class PlatformController {

	@Autowired
	private PlatformRepository repo;

	@RequestMapping(value="/platform", method= RequestMethod.POST)
	public @ResponseBody
	HttpEntity<Platform> addPlatform(@RequestBody Platform platform) {
		System.out.println( "Adding Platform");
		Platform savedPlatform = repo.save(platform);
		System.out.println( "Platform added! : " + savedPlatform + ". Sending message...");
		//Sending message
		PlatformRegistrationPublisher.getInstance().sendPlatformCreatedMessage( savedPlatform );
		return new ResponseEntity<Platform>( savedPlatform, HttpStatus.OK);
	}
}

@RepositoryRestController
class InformationModelController {

	@Autowired
	private InformationModelRepository repo;

	@RequestMapping(value="/informationmodel", method= RequestMethod.POST)
	public @ResponseBody
	HttpEntity<InformationModel> addModel(@RequestBody InformationModel model) {
		System.out.println( "Adding Model");
		InformationModel savedModel = repo.save(model);
		System.out.println( "Model added! : " + savedModel + ". Sending message...");
		//Sending message
		PlatformRegistrationPublisher.getInstance().sendModelCreatedMessage(savedModel);
		return new ResponseEntity<InformationModel>( savedModel, HttpStatus.OK);
	}
}

//@RestController
//class RegistrationController {
//
//	private static Log log = LogFactory.getLog(RegistrationController.class);
//
//	private static final String DIRECTORY = "/corePlatformTriplestore";
//
//
//	public RegistrationController() {
//	}
//
//	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes = "application/json")
//    String register(@RequestBody Platform platform ) {
//		String platformUri = PlatformStorage.getInstance(DIRECTORY).store(platform);
//
//		return platformUri;
//	}
////    String register() {
////        return "Platform registered";
////    }
//}