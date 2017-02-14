package io.federecio.dropwizard.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Path("/oauth2")
public class OAuth2Resource {
    public static final String MOCKED_OAUTH2_TOKEN = UUID.randomUUID().toString();

    @GET
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@QueryParam("response_type") String responseType,
                       @QueryParam("client_id") String clientId,
                       @QueryParam("scope") String scope,
                       @QueryParam("redirect_uri") String redirectUri,
                       @QueryParam("realm") String realm) throws URISyntaxException {
        URI redirectWithAccessToken = UriBuilder.fromUri(redirectUri).queryParam("access_token", MOCKED_OAUTH2_TOKEN).build();
        return Response.seeOther(redirectWithAccessToken).build();
    }

}
