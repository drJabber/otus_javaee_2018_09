package rnk.l10.rest.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.v3.jaxrs2.SwaggerSerializers;
//import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.info.License;
//import io.swagger.v3.oas.annotations.servers.Server;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import rnk.l10.exception.ConstraintViolationExceptionMapper;
import rnk.l10.exception.QueryParamExceptionMapper;
import rnk.l10.rest.RnkAnnuitetCreditAccounterImpl;
import rnk.l10.rest.RnkDifferentialCreditAccounterImpl;

import javax.ws.rs.ApplicationPath;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("api")
public class RnkRestApp extends ResourceConfig {
    private static final Logger logger = Logger.getLogger(RnkRestApp.class.getName());
    private static final String ENCODING_PROPERTY = "encoding";

    public RnkRestApp(){

	registerExceptionMapperClasses();
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
        register(RnkDifferentialCreditAccounterImpl.class);
        register(RnkAnnuitetCreditAccounterImpl.class);
    }


    private void registerExceptionMapperClasses(){
        register(ConstraintViolationExceptionMapper.class);
        register(QueryParamExceptionMapper.class);
    }

}
