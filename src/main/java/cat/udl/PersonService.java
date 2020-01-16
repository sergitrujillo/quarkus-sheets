package cat.udl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/persons")
public class PersonService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll() {
        Person person1 = new Person("Obi-Wan", "Kenobi");
        Person person2 = new Person("Leia", "Organa");
        return Arrays.asList(person1,person2);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person hello(@PathParam("id") int id) {
        Person person1 = new Person("Obi-Wan", "Kenobi");
        Person person2 = new Person("Leia", "Organa");
        List<Person> people = Arrays.asList(person1, person2);
        if (id>=0 && id<people.size()){
            return people.get(id);
        }
        throw new NotFoundException(); // to return 404
    }
}