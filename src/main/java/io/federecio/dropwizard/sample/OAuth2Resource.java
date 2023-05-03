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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import io.federecio.dropwizard.swagger.SwaggerOAuth2Configuration;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Path("/oauth2")
public class OAuth2Resource {
  public static final String MOCKED_OAUTH2_TOKEN = UUID.randomUUID().toString();

  private final SwaggerOAuth2Configuration oAuth2Configuration;

  public OAuth2Resource(SwaggerOAuth2Configuration oAuth2Configuration) {
    this.oAuth2Configuration = oAuth2Configuration.clone();
  }

  @GET
  @Path("/auth")
  @Produces(MediaType.APPLICATION_JSON)
  public Response auth(
      @QueryParam("response_type") String responseType,
      @QueryParam("client_id") @DefaultValue("") String clientId,
      @QueryParam("client_secret") @DefaultValue("") String clientSecret,
      @QueryParam("scope") String scope,
      @QueryParam("state") String state,
      @QueryParam("redirect_uri") String redirectUri,
      @QueryParam("realm") @DefaultValue("") String realm)
      throws URISyntaxException {

    try {
      checkArgument(clientId.equals(oAuth2Configuration.getClientId()), "Bad client id");
      if (!isNullOrEmpty(oAuth2Configuration.getClientSecret())) {
        checkArgument(
            clientSecret.equals(oAuth2Configuration.getClientSecret()), "Bad client secret");
      }
      checkArgument(realm.equals(oAuth2Configuration.getRealm()), "Bad realm");
    } catch (IllegalArgumentException e) {
      throw new BadRequestException(e.getMessage());
    }

    URI redirectWithAccessToken =
        UriBuilder.fromUri(redirectUri)
            .queryParam("state", state)
            .queryParam("access_token", MOCKED_OAUTH2_TOKEN)
            .build();
    return Response.seeOther(redirectWithAccessToken).build();
  }
}
