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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;
import io.swagger.jaxrs.config.BeanConfig;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

/**
 * For the meaning of all these properties please refer to Swagger UI documentation
 * or {@see https://github.com/swagger-api/swagger-ui/blob/master/lib/swagger-oauth.js}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwaggerOAuth2Configuration {

    private String clientId;

    private String clientSecret;
    private String realm;
    private String appName;
    private String scopeSeparator;
    private Map<String, String> additionalQueryStringParams = Maps.newHashMap();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getScopeSeparator() {
        return scopeSeparator;
    }

    public void setScopeSeparator(String scopeSeparator) {
        this.scopeSeparator = scopeSeparator;
    }

    public Map<String, String> getAdditionalQueryStringParams() {
        return additionalQueryStringParams;
    }

    public void setAdditionalQueryStringParams(Map<String, String> additionalQueryStringParams) {
        this.additionalQueryStringParams = additionalQueryStringParams;
    }
}
