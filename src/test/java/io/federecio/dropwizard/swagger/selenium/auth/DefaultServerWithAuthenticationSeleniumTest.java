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

import java.util.concurrent.TimeUnit;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.federecio.dropwizard.swagger.TestConfiguration;
import io.federecio.dropwizard.swagger.selenium.SeleniumTest;

public class DefaultServerWithAuthenticationSeleniumTest extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
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

        new Select(driver.findElement(By.id("input_headerSelect")))
                .selectByVisibleText("Auth Header");
        driver.findElement(By.id("input_authHeader"))
                .sendKeys("Bearer " + token);

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

        new Select(driver.findElement(By.id("input_headerSelect")))
                .selectByVisibleText("api_key");
        driver.findElement(By.id("input_apiKey")).sendKeys(apiKey);

        clickOnTryOut("auth_apiKeyDummyEndpoint_content");
        assertResponseCodeIs("auth_apiKeyDummyEndpoint_content", 200);
        assertResponseBodyIs("auth_apiKeyDummyEndpoint_content", apiKey);
    }
}
