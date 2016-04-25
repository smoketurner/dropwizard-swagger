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
import io.federecio.dropwizard.swagger.TestApplicationWithCustomTemplate;
import io.federecio.dropwizard.swagger.TestConfiguration;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class DefaultServerWithCustomTemplateTest
        extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
            TestApplicationWithCustomTemplate.class,
            ResourceHelpers.resourceFilePath(
                    "test-default.yaml"));

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(RULE.getLocalPort(), "/swagger");
    }

    @Test
    public void testCustomLogo() throws Exception {
        driver.get(getSwaggerUrl());
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);


        // Check for custom logo in the template
        By inputSelector = By.xpath("//img[@class=\"logo__img\"]");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .presenceOfElementLocated(inputSelector));

        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .attributeContains(inputSelector, "src", "myassets/dropwizard-logo.png"));
    }
}
