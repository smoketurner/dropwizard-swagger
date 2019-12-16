/*
 * Copyright © 2014 Federico Recio (N/A)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Copyright (C) 2014 Federico Recio
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger;

import io.restassured.RestAssured;
import org.eclipse.jetty.http.HttpStatus;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

public abstract class DropwizardTest extends DropwizardCommonTest {

  protected DropwizardTest(int port, String basePath) {
    super(port, basePath);
  }

  @Test
  public void swaggerIsAvailable() throws Exception {
    RestAssured.expect()
        .statusCode(HttpStatus.OK_200)
        .body(StringContains.containsString(TestResource.OPERATION_DESCRIPTION))
        .when()
        .get(Path.from(basePath, "swagger.json"));
    RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(Path.from(basePath, "swagger"));
    RestAssured.expect()
        .statusCode(HttpStatus.OK_200)
        .when()
        .get(Path.from(basePath, "swagger") + "/");
  }
}
