package eu.h2020.symbiote.messaging;

import eu.h2020.symbiote.core.RDFFormat;
import eu.h2020.symbiote.messaging.model.CreatedPlatform;
import eu.h2020.symbiote.messaging.model.OntologyModel;
import eu.h2020.symbiote.platform.Platform;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Mael on 08/09/2016.
 */
public class PlatformRegistrationPublisher {

    private static String PLATFORM_CREATED_QUEUE = "PlatformCreated";
    private static String MODEL_CREATED_QUEUE = "ModelCreated";
    private static String MAPPING_CREATED_QUEUE = "MappingCreated";


    private static Log log = LogFactory.getLog(PlatformRegistrationPublisher.class);


    private static PlatformRegistrationPublisher singleton;

    static {
        singleton = new PlatformRegistrationPublisher();
    }

    private PlatformRegistrationPublisher() {

    }

    public static PlatformRegistrationPublisher getInstance() {
        return singleton;
    }

    public void sendPlatformCreatedMessage( Long modelId, Platform platform ) {
        try {
            CreatedPlatform createdPlatform = new CreatedPlatform(platform.getId(),platform.getInstance(),platform.getFormat(),modelId);
            RabbitMessager.sendMessage(PLATFORM_CREATED_QUEUE, createdPlatform);
            log.info("Platform " + platform.getId() + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendModelCreatedMessage(Long modelId, String model, RDFFormat format) {
        try {
            OntologyModel ontologyModel = new OntologyModel(modelId,model,format);
            RabbitMessager.sendMessage(MODEL_CREATED_QUEUE, ontologyModel);
            log.info("Model " + modelId + " created message send successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
