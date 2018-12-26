package rnk.t04.rest;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import rnk.t04.ejb.IUserModel;
import rnk.t04.rest.model.UserData;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/start")
@Produces(MediaType.APPLICATION_JSON)
public class GameSvc {
    private static final Logger logger = Logger.getLogger(GameSvc.class.getName());

    @EJB
    IUserModel model;

    @POST
    @Path("/")
    public Response game(@Valid @BeanParam UserData user) throws NamingException{
        model.initialize(null);

        return Response.ok(new Viewable("/index.jsp", model)).build();
    }
}