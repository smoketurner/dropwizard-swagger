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


import java.util.Optional;

import io.dropwizard.Configuration;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;

/**
 * Wrapper around Dropwizard's configuration and the bundle's config that
 * simplifies getting some information from them.
 */
public class ConfigurationHelper {

    private final Configuration configuration;
    private final SwaggerBundleConfiguration swaggerBundleConfiguration;

    /**
     * Constructor
     *
     * @param configuration
     * @param swaggerBundleConfiguration
     */
    public ConfigurationHelper(Configuration configuration,
            SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.configuration = configuration;
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }

    public String getJerseyRootPath() {
        // if the user explicitly defined a path to prefix requests use it
        // instead of derive it
        if (swaggerBundleConfiguration.getUriPrefix() != null) {
            return swaggerBundleConfiguration.getUriPrefix();
        }

        Optional<String> rootPath;

        ServerFactory serverFactory = configuration.getServerFactory();

        if (serverFactory instanceof SimpleServerFactory) {
            rootPath = ((SimpleServerFactory) serverFactory)
                    .getJerseyRootPath();
        } else {
            rootPath = ((DefaultServerFactory) serverFactory)
                    .getJerseyRootPath();
        }

        return stripUrlSlashes(rootPath.orElse("/"));
    }

    public String getUrlPattern() {
        // if the user explicitly defined a path to prefix requests use it
        // instead of derive it
        if (swaggerBundleConfiguration.getUriPrefix() != null) {
            return swaggerBundleConfiguration.getUriPrefix();
        }

        final String applicationContextPath = getApplicationContextPath();
        final String rootPath = getJerseyRootPath();

        String urlPattern;

        if (rootPath.equals("/") && applicationContextPath.equals("/")) {
            urlPattern = "/";
        } else if (rootPath.equals("/")
                && !applicationContextPath.equals("/")) {
            urlPattern = applicationContextPath;
        } else if (!rootPath.equals("/")
                && applicationContextPath.equals("/")) {
            urlPattern = rootPath;
        } else {
            urlPattern = applicationContextPath + rootPath;
        }

        return urlPattern;
    }

    public String getSwaggerUriPath() {
        final String jerseyRootPath = getJerseyRootPath();
        String uriPathPrefix = jerseyRootPath.equals("/") ? "" : jerseyRootPath;
        return uriPathPrefix + "/swagger-static";
    }

    private String getApplicationContextPath() {
        String applicationContextPath;

        ServerFactory serverFactory = configuration.getServerFactory();

        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory)
                    .getApplicationContextPath();
        } else {
            applicationContextPath = ((DefaultServerFactory) serverFactory)
                    .getApplicationContextPath();
        }

        return stripUrlSlashes(applicationContextPath);
    }

    private String stripUrlSlashes(String urlToStrip) {
        if (urlToStrip.endsWith("/*")) {
            urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
        }

        if (urlToStrip.length() > 1 && urlToStrip.endsWith("/")) {
            urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
        }

        return urlToStrip;
    }
}
