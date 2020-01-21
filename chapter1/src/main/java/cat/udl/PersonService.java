package cat.udl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/persons")
public class PersonService {


    @Inject
    PersonsRepository personsRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll() {
        return personsRepository.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person get(@PathParam("id") int id) {
        return personsRepository.get(id)
                .orElseThrow(NotFoundException::new);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Person person, @Context UriInfo uriInfo) {
        int id = personsRepository.add(person);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(id));
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person replace(@PathParam("id") int id, Person person) {
        return personsRepository.replace(id, person)
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person remove(@PathParam("id") int id) {
        return personsRepository.remove(id)
                .orElseThrow(NotFoundException::new);
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON_PATCH_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person update(@PathParam("id") int id, Person person) {
        return replace(id, person);
    }


}