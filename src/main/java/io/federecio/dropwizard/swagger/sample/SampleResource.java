package io.federecio.dropwizard.swagger.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("/")
@Path("/")
public class SampleResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Hello", notes = "Returns hello")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "hello") })
    public Sample hello() {
        return new Sample();
    }
}
