package eu.h2020.symbiote.platform;

import eu.h2020.symbiote.core.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mael on 31/08/2016.
 */
public class PlatformStorage {

    private static Log log = LogFactory.getLog( PlatformStorage.class );

    private static Map<String,PlatformStorage> storages = Collections.synchronizedMap( new HashMap<>() );
    private String storageLocation;

    private Registry core;
    private SearchEngine searchEngine;


    private PlatformStorage( String storageLocation ) {
        log.info( "Starting platform storage based on Apache Jena");
        TripleStore tripleStore = new TripleStore( storageLocation );
        this.storageLocation = storageLocation;
        core = new Registry(tripleStore);
        searchEngine = new SearchEngine(tripleStore);
        log.info( "" );
    }

    public static PlatformStorage getInstance( String storageName ) {
        PlatformStorage storage = null;
        synchronized ( storages ) {
            storage = storages.get(storageName);
            if( storage == null ) {
                storage = new PlatformStorage(storageName);
                storages.put(storageName, storage);
            }
         }
        return storage;
    }

    public String store( Platform platform ) {
        long platformId = -1;
        log.info( "==================================================");
        log.info( "Adding platform ");
        log.info( "model: [");
        log.info( platform.getModel());
        log.info( "]");
        log.info( "");
        log.info( "instance: [");
        log.info( platform.getInstance());
        log.info( "]");
        log.info( "==================================================");


        long modelId = registerModel(platform);
        if( modelId >= 0 ) {
            platformId = registerInstance(modelId, platform);
        }

        log.info(" >>>>>>>>> PLATFORM (and model) CREATED [ " + platformId + " ]  <<<<<<<<<<<");
        return Ontology.getPlatformGraphURI(platformId);
    }

    private long registerModel( Platform platform ) {
        log.info( "Registering model...");
        long i = core.registerModel(platform.getModel(), platform.getFormat());
        log.info( "Model registered! id: " + i);
        return i;
    }

    private long registerInstance( long modelId, Platform platform ) {
        log.info( "Registering platform...");
        long i = core.registerPlatform(modelId, platform.getInstance(), platform.getFormat());
        log.info( "Platform registered! id: " + i);
        return i;
    }

}
