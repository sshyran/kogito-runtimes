/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
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

package org.kie.kogito.quarkus.it.openapi.client;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.kogito.quarkus.it.openapi.client.mocks.AuthSecurityMockService;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
@QuarkusTestResource(AuthSecurityMockService.class)
class ApiWithSecurityContextIT {

    // injected by quarkus
    WireMockServer authWithApiKeyServer;

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void verifyAuthHeaders() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(
                        Collections
                                .singletonMap(
                                        "workflowdata",
                                        Collections.singletonMap("foo", "bar")))
                .post("/thirdparty")
                .then()
                .statusCode(201);

        // verify if the headers were correctly sent
        authWithApiKeyServer
                .verify(postRequestedFor(urlEqualTo(AuthSecurityMockService.CONFIG.getPath()))
                        .withHeader("X-Client-Id", matching("12345"))
                        .withHeader("Authorization", matching("Bearer mytoken")));
    }

}
