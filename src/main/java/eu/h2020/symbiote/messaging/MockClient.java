package eu.h2020.symbiote.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * For messaging tests purposes
 *
 * Created by mateuszl on 07.09.2016.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MockClient {

    private static Log log = LogFactory.getLog( MockClient.class );

    public static void main(String[] args) {

        String message = "";

        try {
            message = RabbitMessager.receiveMessage("registerQueue");
            log.info("Client received the message succesfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(message);
    }
}
