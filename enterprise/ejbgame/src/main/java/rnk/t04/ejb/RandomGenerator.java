package rnk.t04.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.security.SecureRandom;

@Singleton
public class RandomGenerator {

    SecureRandom random;

    @PostConstruct
    void initialize(){
        SecureRandom seeder=new SecureRandom();
        random=new SecureRandom(seeder.generateSeed(32));
    }

    Double getNext(){
        return random.nextDouble();
    }
}
