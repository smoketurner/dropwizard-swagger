package io.federecio.dropwizard.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;

@Api("/")
@Path("/")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(basicAuthDefinions = {
        @BasicAuthDefinition(key = "sample") }))
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
    @ApiOperation(value = "Secret", notes = "Returns secret")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "secret") })
    public Saying secret(@Auth PrincipalImpl user) {
        return new Saying("secret");
    }
}
