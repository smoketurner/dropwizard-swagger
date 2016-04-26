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
package io.federecio.dropwizard.swagger.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.federecio.dropwizard.swagger.TestApplicationWithAssetsAndPathSetProgramatically;
import io.federecio.dropwizard.swagger.TestApplicationWithPathSetProgramatically;
import io.federecio.dropwizard.swagger.TestConfiguration;

public class DefaultServerWithAssetsSeleniumTest extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
            TestApplicationWithAssetsAndPathSetProgramatically.class,
            ResourceHelpers.resourceFilePath("test-default-assets.yaml"));

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(RULE.getLocalPort(),
                TestApplicationWithPathSetProgramatically.BASE_PATH
                        + "/swagger");
    }

    @Test
    public void testApplicationAssetsAreAccessible() throws Exception {
        driver.get(getSwaggerUrl(RULE.getLocalPort(), "") + "/test.html");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        By xpath = By.xpath("//h1");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .textToBePresentInElementLocated(xpath, "test asset"));
    }
}
