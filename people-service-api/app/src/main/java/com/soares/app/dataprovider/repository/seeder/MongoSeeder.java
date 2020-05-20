package com.soares.app.dataprovider.repository.seeder;

import com.soares.app.dataprovider.repository.PeopleRepository;
import com.soares.app.dataprovider.repository.model.PeopleDB;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MongoSeeder {

    private PeopleRepository peopleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedPeopleCollection();
    }

    private void seedPeopleCollection() {
        peopleRepository.deleteAll();

        var peopleDB = new PeopleDB(null, "José", "dos Santos");
        peopleRepository.insert(peopleDB);

        peopleDB = new PeopleDB(null, "Mariana", "Clemente Oliveira");
        peopleRepository.insert(peopleDB);

        peopleDB = new PeopleDB(null, "Luiza", "Vieira Silva");
        peopleRepository.insert(peopleDB);

        peopleDB = new PeopleDB(null, "Carlos", "Lima Ferreira");
        peopleRepository.insert(peopleDB);
    }
}
