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
    private final String validatorUrl;

    public SwaggerView(@Nonnull final String urlPattern,
            @Nonnull final String validatorUrl) {
        super("index.ftl", StandardCharsets.UTF_8);

        if (urlPattern.equals("/")) {
            swaggerAssetsPath = SWAGGER_URI_PATH;
        } else {
            swaggerAssetsPath = urlPattern + SWAGGER_URI_PATH;
        }

        if (urlPattern.equals("/")) {
            contextPath = "";
        } else {
            contextPath = urlPattern;
        }

        this.validatorUrl = validatorUrl;
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
     * Returns whether the validator URL
     */
    public String getValidatorUrl() {
        return validatorUrl;
    }
}
