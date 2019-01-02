package rnk.l10.rest;


import rnk.l10.rest.model.LoginInfo;
import rnk.l10.rest.model.UserInfo;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public interface AuthIntf {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response login(@BeanParam LoginInfo loginInfo, @Context HttpServletRequest request) throws LoginException;

    @POST
    @Path("/logout")
    Response logout(@Context HttpServletRequest request) throws ServletException;
}
