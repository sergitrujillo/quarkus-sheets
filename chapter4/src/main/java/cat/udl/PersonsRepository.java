package cat.udl;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PersonsRepository implements PanacheMongoRepository<Person> {

    public void add(Person person) {
        person.setId(UUID.randomUUID().toString());
        persist(person);
    }

    public List<Person> getAll(int index, int size) {
        return findAll().page(Page.of(index, size)).list();
    }

    public Optional<Person> get(String id) {
        return find("_id", id).singleResultOptional();
    }

    public Optional<Person> replace(String id, Person person) {
        person.setId(id);
        update(person);
        return get(person.getId());
    }

    public Optional<Person> remove(String id) {
        Optional<Person> person = get(id);
        person.ifPresent(this::delete);
        return person;
    }
}


