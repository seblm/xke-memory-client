package fr.xebia.extremememory;

import fr.xebia.extremememory.model.GameResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

import static java.lang.String.format;
import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class ClientApi {
    private final WebTarget target;

    ClientApi(String host) {
        this.target = newBuilder().build().target(format("http://%s:3000", host));
    }

    boolean register(String email) {
        return "ok".equals(target.path("/scores/register")
                .request()
                .post(entity(email, APPLICATION_JSON_TYPE), String.class));
    }

    GameResponse play(long x1, long y1, long x2, long y2) throws WebApplicationException {
        return target.path("/play")
                .request()
                .post(entity(new long[][]{{x1, y1}, {x2, y2}}, APPLICATION_JSON_TYPE), GameResponse.class);
    }
}
