package cat.udl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@ApplicationScoped
public class PersonsRepository {

    @Inject
    MongoClient mongoClient;

    private CodecRegistry getCodecRegistry() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION))
                .register(Person.class)
                .automatic(true).build();
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    }

    private MongoCollection<Person> getCollection() {
        return mongoClient.getDatabase("quarkus-test")
                .getCollection("persons", Person.class)
                .withCodecRegistry(getCodecRegistry());
    }

    public void add(Person person) {
        person.setId(UUID.randomUUID().toString());
        getCollection().insertOne(person);
    }

    public List<Person> getAll() {
        return StreamSupport
                .stream(getCollection().find().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Person> get(String id) {
        return StreamSupport
                .stream(getCollection().find()
                        .filter(Filters.eq("_id", id)).spliterator(), false)
                .findAny();
    }

    public Optional<Person> replace(String id, Person person) {
        getCollection().replaceOne(Filters.eq("_id", id), person);
        return get(person.getId());
    }

    public Optional<Person> remove(String id) {
        Optional<Person> person = get(id);
        getCollection().deleteOne(Filters.eq("_id", id));
        return person;
    }
}
