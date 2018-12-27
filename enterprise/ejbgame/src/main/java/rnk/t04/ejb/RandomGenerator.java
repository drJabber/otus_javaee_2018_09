package rnk.t04.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import java.security.SecureRandom;

@Singleton
@Startup
public class RandomGenerator {

    SecureRandom random;

    @PostConstruct
    private void initialize(){
        SecureRandom seeder=new SecureRandom();
        random=new SecureRandom(seeder.generateSeed(32));
    }

    public Double getNext(){
        return random.nextDouble();
    }
}
