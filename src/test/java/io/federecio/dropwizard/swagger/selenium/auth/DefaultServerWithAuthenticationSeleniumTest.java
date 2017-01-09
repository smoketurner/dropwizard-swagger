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
package io.federecio.dropwizard.swagger.selenium.auth;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.federecio.dropwizard.swagger.TestConfiguration;
import io.federecio.dropwizard.swagger.selenium.SeleniumTest;

public class DefaultServerWithAuthenticationSeleniumTest extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<>(
            TestAuthApplication.class, ResourceHelpers
                    .resourceFilePath("test-default-authenticated.yaml"));

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(RULE.getLocalPort(), "/swagger");
    }

    @Test
    public void testProtectedResourceWithoutAuthorizationShouldReturn401()
            throws Exception {
        driver.get(getSwaggerUrl() + "#!/auth/protectedDummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        clickOnTryOut("auth_protectedDummyEndpoint_content");
        assertResponseCodeIs("auth_protectedDummyEndpoint_content", 401);
    }

    @Test
    public void testProtectedResourceWithAuthorizationShouldReturn200()
            throws Exception {
        driver.get(getSwaggerUrl() + "#!/auth/protectedDummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        String token = "secret";

        driver.findElement(By.className("authorize__btn")).click();
        final List<WebElement> inputs = driver
                .findElements(By.className("input_apiKey_entry"));
        inputs.get(1).sendKeys("Bearer " + token);
        final List<WebElement> buttons = driver
                .findElements(By.className("auth_submit__button"));
        buttons.get(1).click();

        clickOnTryOut("auth_protectedDummyEndpoint_content");
        assertResponseCodeIs("auth_protectedDummyEndpoint_content", 200);
        assertResponseBodyIs("auth_protectedDummyEndpoint_content", token);
    }

    @Test
    public void testApiKeyResourceWithAuthorizationShouldReturn200()
            throws Exception {
        driver.get(getSwaggerUrl() + "#!/auth/apiKeyDummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        String apiKey = "bab0d85f-00ea-4463-9ab2-d564518b120e";

        driver.findElement(By.className("authorize__btn")).click();
        final List<WebElement> inputs = driver
                .findElements(By.className("input_apiKey_entry"));
        inputs.get(0).sendKeys(apiKey);
        final List<WebElement> buttons = driver
                .findElements(By.className("auth_submit__button"));
        buttons.get(0).click();

        clickOnTryOut("auth_apiKeyDummyEndpoint_content");
        assertResponseCodeIs("auth_apiKeyDummyEndpoint_content", 200);
        assertResponseBodyIs("auth_apiKeyDummyEndpoint_content", apiKey);
    }
}
