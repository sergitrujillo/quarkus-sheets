package cat.udl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/persons")
public class PersonService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson() {
        Person person = new Person("Obi-Wan", "Kenobi");
        return person;
    }
}