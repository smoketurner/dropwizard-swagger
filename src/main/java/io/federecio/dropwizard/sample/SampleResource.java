/*
 * Copyright © 2014 Federico Recio (N/A)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.sample;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@SecuritySchemes({
  @SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "basic"),
  @SecurityScheme(
      type = SecuritySchemeType.OAUTH2,
      flows = @OAuthFlows(implicit = @OAuthFlow(authorizationUrl = "/oauth2/auth")))
})
public class SampleResource {

  @GET
  @Path("/hello")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "Hello",
      description = "Returns hello",
      responses = {@ApiResponse(responseCode = "200", description = "hello")})
  public Saying hello() {
    return new Saying("hello");
  }

  @GET
  @Path("/secret")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "Secret",
      description = "Returns secret",
      responses = {
        @ApiResponse(responseCode = "200", description = "secret"),
        @ApiResponse(
            responseCode = "401",
            description = "Please enter basic credentials or use oauth2 authentication"),
      })
  public Saying secret(@Parameter(hidden = true) @Auth PrincipalImpl user) {
    return new Saying(String.format("Hi %s! It's a secret message...", user.getName()));
  }
}
