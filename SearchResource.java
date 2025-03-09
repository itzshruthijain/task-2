import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @POST
    public Response search(Search searchRequest) {
        if (searchRequest.getCity() == null || searchRequest.getCity().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("City cannot be empty").build();
        }

        List<SearchResult> filteredResults = HoenScannerApplication.getSearchResults()
            .stream()
            .filter(result -> result.getCity().equalsIgnoreCase(searchRequest.getCity()))
            .collect(Collectors.toList());

        return Response.ok(filteredResults).build();
    }
}
