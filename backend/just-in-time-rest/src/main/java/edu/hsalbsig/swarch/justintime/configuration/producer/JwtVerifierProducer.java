package edu.hsalbsig.swarch.justintime.configuration.producer;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

@Singleton
public class JwtVerifierProducer {

    private JWTVerifier verifier;
    
    public JwtVerifierProducer() {
        Algorithm algorithm = Algorithm.HMAC256("vollTollerSchluessel");
        verifier = JWT.require(algorithm).withIssuer("Justin-Time").build();
    }
    
    @Produces
    public JWTVerifier produceVerifier() {
        return verifier;
    }
}
