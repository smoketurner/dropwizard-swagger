// Copyright (C) 2014 Federico Recio
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
package io.federecio.dropwizard.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.swagger.converter.ModelConverters;
import io.swagger.jackson.ModelResolver;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * A {@link io.dropwizard.ConfiguredBundle} that provides hassle-free
 * configuration of Swagger and Swagger UI on top of Dropwizard.
 */
public abstract class SwaggerBundle<T extends Configuration>
        implements ConfiguredBundle<T> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new ViewBundle<Configuration>());
        ModelConverters.getInstance()
                .addConverter(new ModelResolver(bootstrap.getObjectMapper()));
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        SwaggerBundleConfiguration swaggerBundleConfiguration = getSwaggerBundleConfiguration(
                configuration);
        if (swaggerBundleConfiguration == null) {
            throw new IllegalStateException(
                    "You need to provide an instance of SwaggerBundleConfiguration");
        }

        ConfigurationHelper configurationHelper = new ConfigurationHelper(
                configuration, swaggerBundleConfiguration);
        new AssetsBundle("/swagger-static",
                configurationHelper.getSwaggerUriPath(), null, "swagger-assets")
                        .run(environment);

        environment.getObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        swaggerBundleConfiguration.build(configurationHelper.getUrlPattern());

        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(new SwaggerSerializers());
        environment.jersey().register(
                new SwaggerResource(configurationHelper.getUrlPattern(),
                        swaggerBundleConfiguration.getValidatorUrl()));
    }

    protected abstract SwaggerBundleConfiguration getSwaggerBundleConfiguration(
            T configuration);
}
