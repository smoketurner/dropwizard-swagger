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

import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import io.dropwizard.views.View;

/**
 * Serves the content of Swagger's index page which has been "templatized" to
 * support replacing the directory in which Swagger's static content is located
 * (i.e. JS files) and the path with which requests to resources need to be
 * prefixed.
 */
public class SwaggerView extends View {

    private static final String SWAGGER_URI_PATH = "/swagger-static";

    private final String swaggerAssetsPath;
    private final String contextPath;

    private final SwaggerViewConfiguration viewConfiguration;
    private final SwaggerOAuth2Configuration oauth2Configuration;

    public SwaggerView(@Nonnull final String contextRoot,
            @Nonnull final String urlPattern,
            @Nonnull SwaggerViewConfiguration viewConfiguration,
            @Nonnull SwaggerOAuth2Configuration oauth2Configuration) {
        super(viewConfiguration.getTemplateUrl(), StandardCharsets.UTF_8);

        String contextRootPrefix = "/".equals(contextRoot) ? "" : contextRoot;

        // swagger-static should be found on the root context
        if (!contextRootPrefix.isEmpty()) {
            swaggerAssetsPath = contextRootPrefix + SWAGGER_URI_PATH;
        } else {
            swaggerAssetsPath = (urlPattern.equals("/") ? SWAGGER_URI_PATH
                    : (urlPattern + SWAGGER_URI_PATH));
        }

        contextPath = urlPattern.equals("/") ? contextRootPrefix
                : (contextRootPrefix + urlPattern);

        this.viewConfiguration = viewConfiguration;
        this.oauth2Configuration = oauth2Configuration;
    }

    /**
     * Returns the title for the browser header
     */
    public String getTitle() {
        return viewConfiguration.getPageTitle();
    }

    /**
     * Returns the path with which all requests for Swagger's static content
     * need to be prefixed
     */
    public String getSwaggerAssetsPath() {
        return swaggerAssetsPath;
    }

    /**
     * Returns the path with with which all requests made by Swagger's UI to
     * Resources need to be prefixed
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Returns the location of the validator URL or null to disable
     */
    public String getValidatorUrl() {
        return viewConfiguration.getValidatorUrl();
    }

    /**
     * Returns whether to display the authorization input boxes
     */
    public boolean getShowAuth() {
        return viewConfiguration.isShowAuth();
    }

    /**
     * Returns whether to display the swagger spec selector
     */
    public boolean getShowApiSelector() {
        return viewConfiguration.isShowApiSelector();
    }

    /**
     * @return {@link SwaggerOAuth2Configuration} containing every properties to
     *         init oauth2
     */
    public SwaggerOAuth2Configuration getOauth2Configuration() {
        return oauth2Configuration;
    }
}
