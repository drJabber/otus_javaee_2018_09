package rnk.l10.rest;

import org.glassfish.jersey.server.mvc.Viewable;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;
import rnk.l10.rest.model.StaffEditorModel;
import rnk.l10.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public class RnkStaffEditorImpl implements RnkStaffEditor {
    @Context
    HttpServletRequest request;

    @Override
    @GET
    @Produces("text/html;")
    @Path("/staffeditor/{id}")
    public Response edit(@PathParam("id") String id) throws RnkWebServiceException {
        StaffEditorModel model = new StaffEditorModel(id);
        model.setPage("WEB-INF/admin-staff-editor.jsp");

        model.setSubmitPage("/api/v2/staff/" + id);
        model.setSubmitMethod("PUT");
        model.setCancelPage(UrlUtils.getUrl(request) + "/main/admin/staff");
        return Response.ok(new Viewable("/index.jsp", model)).build();
    }

    @Override
    @GET
    @Produces("text/html;")
    @Path("/staffremover/{id}")
    public Response remove(@PathParam("id") String id) throws RnkWebServiceException {
        StaffEditorModel model = new StaffEditorModel(id);
        model.setPage("WEB-INF/admin-staff-remover.jsp");

        model.setSubmitPage("/api/v2/staff/" + id);
        model.setSubmitMethod("DELETE");
        model.setCancelPage(UrlUtils.getUrl(request) + "/main/admin/staff");
        return Response.ok(new Viewable("/index.jsp", model)).build();
    }

}