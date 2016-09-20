package eu.h2020.symbiote;

import eu.h2020.symbiote.messaging.RegistrationPublisher;
import eu.h2020.symbiote.repository.InformationModel;
import eu.h2020.symbiote.repository.Mapping;
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
import org.springframework.web.bind.annotation.*;

@EnableDiscoveryClient
@SpringBootApplication
public class CoreRegistrationServiceApplication {

	public static Log log = LogFactory.getLog(CoreRegistrationServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CoreRegistrationServiceApplication.class, args);
	}

}

@RepositoryRestResource(collectionResourceRel = "platform", path = "platform")
interface PlatformRepository extends MongoRepository<Platform, String> {


}

@RepositoryRestResource(collectionResourceRel = "informationmodel", path = "informationmodel")
interface InformationModelRepository extends MongoRepository<InformationModel, String> {

}

@RepositoryRestResource(collectionResourceRel = "mapping", path = "mapping")
interface MappingRepository extends MongoRepository<Mapping, String> {

}

@CrossOrigin
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
		RegistrationPublisher.getInstance().sendPlatformCreatedMessage( savedPlatform );
		return new ResponseEntity<Platform>( savedPlatform, HttpStatus.OK);
	}
}

@CrossOrigin
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
		RegistrationPublisher.getInstance().sendModelCreatedMessage(savedModel);
		return new ResponseEntity<InformationModel>( savedModel, HttpStatus.OK);
	}
}

@CrossOrigin
@RepositoryRestController
class MappingController {

	@Autowired
	private MappingRepository repo;

	@RequestMapping(value="/mapping", method= RequestMethod.POST)
	public @ResponseBody
	HttpEntity<Mapping> addModel(@RequestBody Mapping mapping) {
		System.out.println( "Adding Mapping");
		Mapping savedMapping = repo.save(mapping);
		System.out.println( "Mapping added! : " + mapping + ". Sending message...");
		//Sending message
		RegistrationPublisher.getInstance().sendMappingCreatedMessage(savedMapping);
		return new ResponseEntity<Mapping>( savedMapping, HttpStatus.OK);
	}
}