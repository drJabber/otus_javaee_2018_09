package rnk.l10.rest;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.glassfish.jersey.server.model.ResourceMethod;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Parameter;

@Provider
public class RnkAccounterParameterFilter implements ContainerRequestFilter
{
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        // Loop through each parameter
        ResourceMethod method = resourceInfo.getResourceMethod();
        method.
        for (Parameter parameter : resourceInfo.getResourceMethod().getParameters())
        {
            // Check is this parameter is a query parameter
            QueryParam queryAnnotation = parameter.getAnnotation(QueryParam.class);

            // ... and whether it is a required one
            if (queryAnnotation != null && parameter.isAnnotationPresent(Required.class))
            {
                // ... and whether it was not specified
                if (!requestContext.getUriInfo().getQueryParameters().containsKey(queryAnnotation.value()))
                {
                    // We pass the query variable name to the constructor so that the exception can generate a meaningful error message
                    throw new BadRequestException(queryAnnotation.value());
                }
            }
        }
    }
}