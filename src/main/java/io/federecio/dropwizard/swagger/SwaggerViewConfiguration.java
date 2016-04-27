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

import javax.annotation.Nonnull;

/**
 * Contains all configurable parameters required to render the SwaggerUI View from the default template
 */
public class SwaggerViewConfiguration {


    private String pageTitle;
    private String templateUrl;
    private String validatorUrl;
    private boolean showApiSelector;
    private boolean showAuth;

    private static final String DEFAULT_TITLE = "Swagger UI";
    private static final String DEFAULT_TEMPLATE = "index.ftl";

    public SwaggerViewConfiguration () {
        this.pageTitle = DEFAULT_TITLE;
        this.templateUrl = DEFAULT_TEMPLATE;
        this.validatorUrl = null;
        this.showApiSelector = true;
        this.showAuth = true;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String title) {
        this.pageTitle = title;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getValidatorUrl() {
        return validatorUrl;
    }

    public void setValidatorUrl(String validatorUrl) {
        this.validatorUrl = validatorUrl;
    }

    public boolean isShowApiSelector() {
        return showApiSelector;
    }

    public void setShowApiSelector(boolean showApiSelector) {
        this.showApiSelector = showApiSelector;
    }

    public boolean isShowAuth() {
        return showAuth;
    }

    public void setShowAuth(boolean showAuth) {
        this.showAuth = showAuth;
    }
}
