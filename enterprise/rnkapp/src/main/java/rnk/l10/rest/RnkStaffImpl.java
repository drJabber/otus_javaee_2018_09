package rnk.l10.rest;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import rnk.l10.entities.StaffEntity;
import rnk.l10.entities.beans.SearchResultCache;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.rest.model.StaffDto;
import rnk.l10.utils.StaffUtils;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
//import java.util.List;

@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class RnkStaffImpl implements RnkStaff{

    @Context
    UriInfo uriInfo;

    @Context
    ServletContext context;

    @Override
    @PUT
    @Path("/staff/{id}")
//    @Operation(
//            summary="изменение данных сотрудников",
//            tags = {"Изменеие","Сотрудник"},
//            description="Изменение данных сотрудника",
//            responses = {@ApiResponse(description = "Изменение"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response edit(
//            @Parameter(description = "Данные сотрудника", required = true)
            @Valid
            @BeanParam StaffDto staff
    )throws RnkWebServiceException{
        staff.save();
        invalidateCache(staff.getId());
        URI uri=uriInfo.getAbsolutePathBuilder().replacePath("/rnkapp/main/admin/staff").build();
        return Response.seeOther(uri).build();
    }

    @Override
    @DELETE
    @Path("/staff/{id}")
//    @Operation(
//            summary="удаление данных сотрудников",
//            tags = {"Удаленеие","Сотрудник"},
//            description="Удаление данных сотрудника",
//            responses = {@ApiResponse(description = "Удаление"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response remove(
//            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathParam(value = "id") Integer id
    )throws RnkWebServiceException{
        StaffUtils.removeStaff(id);
        invalidateCache(id);

        return Response.ok().build();
    }

    @Override
    @POST
    @Path("/staff")
//    @Operation(
//            summary="добавление данных сотрудников",
//            tags = {"Изменеие","Сотрудник"},
//            description="Добавление данных сотрудника",
//            responses = {@ApiResponse(description = "Добавление"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
//                    @ApiResponse(responseCode = "404", description = "Параметр не найден")
//            }
//    )
//    @RolesAllowed("admin")
    public Response add( @Valid @BeanParam StaffDto staff) throws RnkWebServiceException {
        staff.save();
        invalidateCache(null);

        URI uri=uriInfo.getAbsolutePathBuilder().replacePath("/rnkapp/main/admin/staff").build();
        return Response.seeOther(uri).build();
    }

    private void invalidateCache(Integer staffId){
        SearchResultCache cache= (SearchResultCache)context.getAttribute("admin-search-cache");
        if (cache!=null){
            if (staffId!=null){
                cache.invalidateStaff(staffId);
            }else{
                cache.invalidateAll();
            }
        }
    }
}
