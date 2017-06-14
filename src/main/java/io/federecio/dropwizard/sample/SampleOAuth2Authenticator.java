package io.federecio.dropwizard.sample;

import static io.federecio.dropwizard.sample.OAuth2Resource.MOCKED_OAUTH2_TOKEN;
import java.util.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;

public class SampleOAuth2Authenticator
        implements Authenticator<String, PrincipalImpl> {

    @Override
    public Optional<PrincipalImpl> authenticate(String token)
            throws AuthenticationException {
        if (MOCKED_OAUTH2_TOKEN.equals(token)) {
            return Optional.of(new PrincipalImpl("oauth2 user"));
        }
        return Optional.empty();
    }
}
