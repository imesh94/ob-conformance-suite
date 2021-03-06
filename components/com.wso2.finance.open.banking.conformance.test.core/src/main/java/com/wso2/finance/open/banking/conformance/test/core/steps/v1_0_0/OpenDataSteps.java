
/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0;

import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import com.wso2.finance.open.banking.conformance.test.core.request.RequestGenerator;
import com.wso2.finance.open.banking.conformance.test.core.response.ResponseValidator;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Utils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


/**
 * Steps used by open data tests.
 */
public class OpenDataSteps {

    private RequestGenerator requestGenerator = new RequestGenerator();
    private ResponseValidator responseValidator;
    private RequestSpecification httpRequest;
    private Response response;

    private String endpointGetAtmsByBankId;
    private String endpointGetBranchesByBankId;
    private String endpointGetProductsByBankId;

    private Logger log = Logger.getLogger(OpenDataSteps.class);

    @Given("a request is initiated to ATM endpoint")
    public void createRequestToAtmEndpoint() {

        endpointGetAtmsByBankId = "/v1.0.0/banks/" + Context.getInstance()
                .getCurrentFeatureAttribute("uri", "bank_id") + "/atms";
        httpRequest = requestGenerator.generate();
    }

    @When("a user retrieves the atm details")
    public void getAtmDetails() {

        response = given().spec(httpRequest).when().get(endpointGetAtmsByBankId);
        responseValidator = new ResponseValidator(response);
        log.info("response: " + response.getBody().asString());
    }

    @Then("response json data should be compliant to the standard")
    public void validateResponse() {

        responseValidator.validateResponse();
    }

    @Given("a request is initiated to BRANCH endpoint")
    public void createRequestToBranchEndpoint() {

        endpointGetBranchesByBankId = "/v1.0.0/banks/" + Context.getInstance()
                .getCurrentFeatureAttribute("uri", "bank_id") + "/branches";
        httpRequest = requestGenerator.generate();
    }

    @When("a user retrieves the branch details")
    public void getBranchDetails() {

        response = given().spec(httpRequest).when().get(endpointGetBranchesByBankId);
        log.info("response: " + response.getBody().asString());
    }

    @Given("a request is initiated to PRODUCT endpoint")
    public void createRequestToProductEndpoint() {

        endpointGetProductsByBankId = "/v1.0.0/banks/" + Context.getInstance()
                .getCurrentFeatureAttribute("uri", "bank_id") + "/products";
        httpRequest = requestGenerator.generate();
    }

    @When("a user retrieves the product details")
    public void getProductDetails() {

        response = given().spec(httpRequest).when().get(endpointGetProductsByBankId);
        log.info("response: " + response.getBody().asString());
    }

    @Then("response json data should contain geo-location of the branch")
    public void validateGeoLocation() {

        assertEquals(Utils.formatError("Response does not contain geo-location of the bank"), false, true);
    }

}
