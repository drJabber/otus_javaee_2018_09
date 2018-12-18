package rnk.l10.rest;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.glassfish.jersey.server.mvc.Viewable;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;
import rnk.l10.rest.model.StaffEditorModel;
import rnk.l10.utils.UrlUtils;

import javax.annotation.security.RolesAllowed;
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
import java.util.List;
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
//    @Operation(
//            summary="редактирование сотрудников",
//            tags = {"Редактор","Сотрудник"},
//            description="Представление для изменения данных сотрудника",
//            responses = {@ApiResponse(description = "Представление редактора"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response edit(
//            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathParam("id") String id
    ) throws RnkWebServiceException {
        StaffEditorModel model = new StaffEditorModel(id);
        model.setPage("WEB-INF/admin-staff-editor.jsp");

        model.setSubmitPage("/rnkapp/api/v2/staff/" + id);
        model.setCancelPage(UrlUtils.getUrl(request) + "/rnkapp/main/admin/staff");
        return Response.ok(new Viewable("/index.jsp", model)).build();
    }

    @Override
    @GET
    @Produces("text/html;")
    @Path("/staffeditor")
//    @Operation(
//            summary="добавление сотрудников",
//            tags = {"Редактор","Сотрудник"},
//            description="Представление создания записи о сотрудник",
//            responses = {@ApiResponse(description = "Представление редактора"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response add() throws RnkWebServiceException {
        StaffEditorModel model = new StaffEditorModel();
        model.setPage("WEB-INF/admin-staff-editor.jsp");

        model.setSubmitPage("/rnkapp/api/v2/staff");
        model.setCancelPage(UrlUtils.getUrl(request) + "/rnkapp/main/admin/staff");
        return Response.ok(new Viewable("/index.jsp", model)).build();
    }

    @Override
    @GET
    @Produces("text/html;")
    @Path("/staffremover/{id}")
//    @Operation(
//            summary="удаление сотрудников",
//            tags = {"Удаление","Сотрудник"},
//            description="Представление для удаления данных сотрудника",
//            responses = {@ApiResponse(description = "Представление удаления"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response remove(
//            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathParam("id") String id
    ) throws RnkWebServiceException {
        StaffEditorModel model = new StaffEditorModel(id);
        model.setPage("WEB-INF/admin-staff-remover.jsp");

        model.setSubmitPage("/rnkapp/api/v2/staff/" + id);
        model.setSubmitMethod("DELETE");
        model.setCancelPage(UrlUtils.getUrl(request) + "/rnkapp/main/admin/staff");
        return Response.ok(new Viewable("/index.jsp", model)).build();
    }

}