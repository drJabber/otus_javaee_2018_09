package rnk.l10.rest.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import rnk.l10.exception.ConstraintViolationExceptionMapper;
import rnk.l10.exception.QueryParamExceptionMapper;
import rnk.l10.exception.RnkExceptionMapper;
import rnk.l10.rest.RnkAnnuitetCreditAccounterImpl;
import rnk.l10.rest.RnkDifferentialCreditAccounterImpl;
import rnk.l10.rest.RnkStaffEditorImpl;

import javax.ws.rs.ApplicationPath;
import java.nio.charset.StandardCharsets;

@ApplicationPath("/api")
public class RnkRestApp extends ResourceConfig {
    private static final String ENCODING_PROPERTY = "encoding";

    public RnkRestApp(){
        property(JspMvcFeature.TEMPLATE_BASE_PATH,"/");
        register(JspMvcFeature.class);

        registerResourceClasses();
        registerExceptionMapperClasses();
        registerValidationClasses();
        registerFilterClasses();

        // publish swagger doc
        registerSwaggerClasses();
        publishSwaggerDescription();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        register(jacksonProvider);

        property(ENCODING_PROPERTY, StandardCharsets.UTF_8.toString());
    }

    private void registerResourceClasses() {
        register(RnkDifferentialCreditAccounterImpl.class);
        register(RnkAnnuitetCreditAccounterImpl.class);
        register(RnkStaffEditorImpl.class);
    }


    private void registerExceptionMapperClasses(){
        register(ConstraintViolationExceptionMapper.class);
        register(QueryParamExceptionMapper.class);
    }

    private void registerFilterClasses(){
//        register(RnkCorsFilter.class);
    }

    private void registerValidationClasses(){

    }
    private void registerSwaggerClasses() {
//        register(ApiListingResource.class);
//        register(SwaggerSerializers.class);
    }

    public void publishSwaggerDescription(){
        //SwaggerUI http://localhost:8080/api-docs
        //Tomcat suitable approach swagger declaration
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setTitle("Swagger JAX-RS Integration Example");
//        beanConfig.setDescription("A simple Maven JAX-RS project.");
//        beanConfig.setVersion("1.0.0");
//        beanConfig.setSchemes(new String[]{"http"});
//        beanConfig.setBasePath("/api");
////        beanConfig.setResourcePackage(RESOURCE_PACKAGE);
//        beanConfig.setPrettyPrint(true);
//        beanConfig.setScan(true);
    }
}
