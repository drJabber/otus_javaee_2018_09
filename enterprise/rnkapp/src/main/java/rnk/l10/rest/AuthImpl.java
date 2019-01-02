package rnk.l10.rest;

import rnk.l10.rest.model.LoginInfo;
import rnk.l10.rest.model.UserInfo;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/auth")
public class AuthImpl implements AuthIntf {


    @Context
    UriInfo uriInfo;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response login(LoginInfo loginInfo, @Context HttpServletRequest request) throws LoginException{
        try {
            request.login(loginInfo.getLogin(),loginInfo.getPassword());
            return Response.ok().build();
        }catch (Exception ex){
            throw new LoginException("Cant login application") ;
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response logout(@Context HttpServletRequest request) throws ServletException {
        request.logout();
        return Response.ok().build();

    }
}
