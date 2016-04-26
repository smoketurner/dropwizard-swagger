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
package io.federecio.dropwizard.swagger;

import org.junit.ClassRule;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class SimpleServerWithRootPathSetTest extends DropwizardTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
            TestApplication.class, ResourceHelpers
                    .resourceFilePath("test-simple-with-root-path.yaml"));

    public SimpleServerWithRootPathSetTest() {
        super(RULE.getLocalPort(), "/application/api");
    }
}
