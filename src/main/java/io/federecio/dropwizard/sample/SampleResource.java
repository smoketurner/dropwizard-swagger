package io.federecio.dropwizard.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.OAuth2Definition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;

@Api("/")
@Path("/")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(basicAuthDefinitions = {
        @BasicAuthDefinition(key = "basic") }, oAuth2Definitions = {
                @OAuth2Definition(flow = OAuth2Definition.Flow.IMPLICIT, key = "oauth2", authorizationUrl = "/oauth2/auth") }))
public class SampleResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Hello", notes = "Returns hello")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "hello") })
    public Saying hello() {
        return new Saying("hello");
    }

    @GET
    @Path("/secret")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Secret", notes = "Returns secret", authorizations = {
            @Authorization("basic"), @Authorization("oauth2") })
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Please enter basic credentials or use oauth2 authentication"),
            @ApiResponse(code = 200, message = "secret") })
    public Saying secret(@ApiParam(hidden = true) @Auth PrincipalImpl user) {
        return new Saying(String.format("Hi %s! It's a secret message...",
                user.getName()));
    }
}
