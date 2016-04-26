//  Copyright (C) 2014 Federico Recio
/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger.selenium.auth;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.TestApplication;
import io.federecio.dropwizard.swagger.TestConfiguration;

public class TestAuthApplication extends TestApplication {

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        super.run(configuration, environment);
        environment.jersey().register(new AuthResource());

        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<PrincipalImpl>()
                    .setAuthenticator(new TestAuthenticator())
                    .setRealm("SUPER SECRET STUFF")
                    .setPrefix("Bearer")
                    .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(PrincipalImpl.class));
    }
}
