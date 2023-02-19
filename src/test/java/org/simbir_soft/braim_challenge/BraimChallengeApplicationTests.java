package org.simbir_soft.braim_challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BraimChallengeApplicationTests {

    @Test
    void contextLoads() throws Throwable{
        Animal animal = new Animal();
        AnimalType type = new AnimalType();
        type.setId(1L);
        Account chipper = new Account();
        chipper.setId(1L);
        animal.getAnimalTypes().add(type);
        animal.setChipper(chipper);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(animal);
        System.out.println(s);
    }

}
