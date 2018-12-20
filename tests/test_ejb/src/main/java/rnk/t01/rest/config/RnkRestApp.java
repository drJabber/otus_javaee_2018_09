package rnk.t01.rest.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import rnk.t01.rest.TestServiceImpl;

import javax.ws.rs.ApplicationPath;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("api")
public class RnkRestApp extends ResourceConfig {
    private static final Logger logger = Logger.getLogger(RnkRestApp.class.getName());
    private static final String ENCODING_PROPERTY = "encoding";

    public RnkRestApp(){

        registerResourceClasses();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        register(jacksonProvider);

        logger.setLevel(Level.ALL);
        register(new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY,65536));

        property(ENCODING_PROPERTY, StandardCharsets.UTF_8.toString());
    }

    private void registerResourceClasses() {
        register(TestServiceImpl.class);
    }
}
