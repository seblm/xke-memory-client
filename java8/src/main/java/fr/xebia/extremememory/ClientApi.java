package fr.xebia.extremememory;

import fr.xebia.extremememory.model.GameResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.WebTarget;

import static java.lang.String.format;
import static javax.ws.rs.client.ClientBuilder.newBuilder;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class ClientApi {
    private final WebTarget target;

    ClientApi(String host) {
        this(host, 3000);
    }
    
    ClientApi(String host, int port) {
        this.target = newBuilder().build().target(format("http://%s:%d", host, port));
    }

    boolean register(String email) {
        return "ok".equals(target.path("/scores/register")
                .request()
                .post(entity(email, APPLICATION_JSON_TYPE), String.class));
    }

    GameResponse play(long x1, long y1, long x2, long y2) {
        try {
            return target.path("/play")
                    .request()
                    .post(entity(new long[][]{{x1, y1}, {x2, y2}}, APPLICATION_JSON_TYPE), GameResponse.class);
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getResponse().readEntity(String.class), e);
        }
    }
}
