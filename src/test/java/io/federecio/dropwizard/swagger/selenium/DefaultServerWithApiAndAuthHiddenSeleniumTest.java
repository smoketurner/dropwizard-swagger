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
package io.federecio.dropwizard.swagger.selenium;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.federecio.dropwizard.swagger.TestApplicationWithAuthAndApiSelectorDisabled;
import io.federecio.dropwizard.swagger.TestConfiguration;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class DefaultServerWithApiAndAuthHiddenSeleniumTest
        extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
            TestApplicationWithAuthAndApiSelectorDisabled.class,
            ResourceHelpers.resourceFilePath(
                    "test-default.yaml"));

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(RULE.getLocalPort(), "/swagger");
    }

    @Test
    public void testFieldsAssetsAreNotVisible() throws Exception {
        driver.get(getSwaggerUrl());
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        // Check for invisibility of JSON document selector
        By inputSelector = By.xpath("#input_baseUrl");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .invisibilityOfElementLocated(inputSelector));
        By explore = By.xpath("#explore");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .invisibilityOfElementLocated(explore));

        // Check for invisilibity of auth fields
        By apiKey = By.xpath("#input_apiKey");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .invisibilityOfElementLocated(apiKey));
        By authHeader = By.xpath("#input_authHeader");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .invisibilityOfElementLocated(authHeader));
        By apiSelector = By.xpath("#input_headerSelect");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .invisibilityOfElementLocated(apiSelector));
    }
}
