package fr.xebia.extremememory;

import fr.xebia.extremememory.model.GameResponse;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

import static java.lang.String.format;
import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class ClientApi {
    private final WebTarget target;

    ClientApi(String host) {
        this.target = newBuilder().register(JacksonFeature.class).build().target(format("http://%s:3000", host));
    }

    void register(String email) {
        target.path("/scores/register")
                .request()
                .post(entity(email, APPLICATION_JSON_TYPE));
    }

    GameResponse play(long[][] coords) throws WebApplicationException {
        return target.path("/play")
                .request()
                .post(entity(coords, APPLICATION_JSON_TYPE), GameResponse.class);
    }
}
