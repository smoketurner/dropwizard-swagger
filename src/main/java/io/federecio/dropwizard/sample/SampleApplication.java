package io.federecio.dropwizard.sample;

import com.google.common.collect.Lists;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import java.util.List;

public class SampleApplication extends Application<SampleConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "sample";
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<SampleConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    SampleConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    };

    @Override
    public void run(SampleConfiguration configuration, Environment environment)
            throws Exception {
        AuthFilter basicCredentialAuthFilter = new BasicCredentialAuthFilter.Builder<PrincipalImpl>()
            .setAuthenticator(new SampleBasicAuthenticator())
            .setPrefix("Basic")
            .buildAuthFilter();

        AuthFilter oauthCredentialAuthFilter = new OAuthCredentialAuthFilter.Builder<PrincipalImpl>()
            .setAuthenticator(new SampleOAuth2Authenticator())
            .setPrefix("Bearer")
            .buildAuthFilter();

        List<AuthFilter> filters = Lists.newArrayList(basicCredentialAuthFilter, oauthCredentialAuthFilter);
        environment.jersey().register(new AuthDynamicFeature(new ChainedAuthFilter(filters)));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(PrincipalImpl.class));

        // resources
        environment.jersey().register(new SampleResource());
        environment.jersey().register(new OAuth2Resource(configuration.getSwagger().getSwaggerOAuth2Configuration()));
    }
}
