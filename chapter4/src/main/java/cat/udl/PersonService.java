package cat.udl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/persons")
public class PersonService {


    @Inject
    PersonsRepository personsRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll(@QueryParam("size") @DefaultValue("10") int size,
                               @QueryParam("page") @DefaultValue("0") int page) {
        return personsRepository.getAll(page, size);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person get(@PathParam("id") String id) {
        return personsRepository.get(id)
                .orElseThrow(NotFoundException::new);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Person person, @Context UriInfo uriInfo) {
        personsRepository.add(person);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(person.getId());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person replace(@PathParam("id") String id, Person person) {
        return personsRepository.replace(id, person)
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person remove(@PathParam("id") String id) {
        return personsRepository.remove(id)
                .orElseThrow(NotFoundException::new);

    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON_PATCH_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person update(@PathParam("id") String id, Person person) {
        return replace(id, person);
    }


}