package cat.udl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonsRepository {

    ArrayList<Person> personList;

    @PostConstruct
    public void init() {
        personList = new ArrayList<>();
        Person person1 = new Person("Obi-Wan", "Kenobi");
        Person person2 = new Person("Leia", "Organa");
        personList.add(person1);
        personList.add(person2);
    }

    public List<Person> getAll(){
        return personList;
    }


    public Optional<Person> get(int id) {
        if (id >= 0 && id < personList.size()) {
            return Optional.of(personList.get(id));
        }
        return Optional.empty();
    }

    public int add(Person person){
        personList.add(person);
        return personList.size()-1;
    }

    public Optional<Person>  replace(int id, Person person){
        if (id >= 0 && id < personList.size()) {
            personList.set(id, person);
            return Optional.of(person);
        }
        return Optional.empty();
    }

    public Optional<Person>  remove(int id){
        if (id >= 0 && id < personList.size()) {
            return Optional.of(personList.remove(id));
        }
        return Optional.empty();
    }
}
