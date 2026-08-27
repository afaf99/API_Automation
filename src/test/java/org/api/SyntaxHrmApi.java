package org.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.utils.ConfigReader;

public class SyntaxHrmApi {

    private final String baseUrl =
            ConfigReader.getProperty("baseUrl");


    public Response login(String body) {

        return RestAssured
                .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)

                .when()
                .post(APIConstants.LOGIN);
    }


    public Response createEmployee(
            String token,
            String body) {

        return RestAssured
                .given()
                .baseUri(baseUrl)
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)

                .when()
                .post(APIConstants.CREATE_EMPLOYEE);
    }


    public Response getEmployee(
            String token,
            int empNumber) {

        return RestAssured
                .given()
                .baseUri(baseUrl)
                .auth()
                .oauth2(token)
                .accept(ContentType.JSON)

                .when()
                .get(
                        APIConstants.EMPLOYEE,
                        empNumber
                );
    }


    public Response updateEmployee(
            String token,
            int empNumber,
            String body) {

        return RestAssured
                .given()
                .baseUri(baseUrl)
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)

                .when()
                .put(
                        APIConstants.EMPLOYEE,
                        empNumber
                );
    }
}
