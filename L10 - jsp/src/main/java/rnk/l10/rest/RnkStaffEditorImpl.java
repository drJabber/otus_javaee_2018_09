package rnk.l10.rest;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/v2/staff")
public class RnkStaffEditorImpl implements RnkStaffEditor{
    @Context
    Request request;

    @Override
    @GET
    @Produces("text/html")
    @Path("/editor")
    public Response edit() {
        return Response.ok(new Viewable("....")).build();
    }
}
