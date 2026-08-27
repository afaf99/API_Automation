package org.stepdefinitions;



import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;

import org.api.SyntaxHrmApi;
import org.context.TestContext;
import org.json.JSONObject;

import org.junit.Assert;
import org.utils.ConfigReader;


public class EmployeeSteps {


    SyntaxHrmApi api =
            new SyntaxHrmApi();


    TestContext context =
            new TestContext();



    @Given("I authenticate to SyntaxHRM")
    public void authenticate() {


        JSONObject loginBody =
                new JSONObject();


        loginBody.put(
                "username",
                ConfigReader
                        .getProperty(
                                "username"
                        )
        );


        loginBody.put(
                "password",
                ConfigReader
                        .getProperty(
                                "password"
                        )
        );


        Response response =
                api.login(
                        loginBody.toString()
                );


        Assert.assertEquals(

                "Login failed: "
                        + response.asString(),

                201,

                response.statusCode()
        );


        String token =
                getToken(response);


        Assert.assertNotNull(

                "JWT token was not found. "
                        + response.asString(),

                token
        );


        context.setToken(
                token
        );
    }



    @When("I create the assessment employee")
    public void createEmployee() {


        JSONObject body =
                employeeBody(
                        "Afaf"
                );


        Response response =
                api.createEmployee(

                        context.getToken(),

                        body.toString()
                );


        context.setResponse(
                response
        );


        if (response.statusCode() == 201) {


            int empNumber =
                    response
                            .jsonPath()
                            .getInt(
                                    "data.empNumber"
                            );


            context.setEmpNumber(
                    empNumber
            );
        }
    }




    @Given("an assessment employee already exists")
    public void employeeAlreadyExists() {


        JSONObject body =
                employeeBody(
                        "Afaf"
                );


        Response response =
                api.createEmployee(

                        context.getToken(),

                        body.toString()
                );


        Assert.assertEquals(

                "Create employee failed: "
                        + response.asString(),

                201,

                response.statusCode()
        );


        int empNumber =
                response
                        .jsonPath()
                        .getInt(
                                "data.empNumber"
                        );


        Assert.assertTrue(
                empNumber > 0
        );


        context.setEmpNumber(
                empNumber
        );


        context.setResponse(
                response
        );
    }




    @When("I get the employee")
    public void getEmployee() {


        Response response =
                api.getEmployee(

                        context.getToken(),

                        context.getEmpNumber()
                );


        context.setResponse(
                response
        );
    }





    @When("I update the employee first name to {string}")
    public void updateEmployee(
            String firstName) {


        JSONObject body =
                employeeBody(
                        firstName
                );


        Response response =
                api.updateEmployee(

                        context.getToken(),

                        context.getEmpNumber(),

                        body.toString()
                );


        context.setResponse(
                response
        );
    }





    @Then("response status code should be {int}")
    public void validateStatusCode(
            int expectedStatus) {


        Response response =
                context.getResponse();


        Assert.assertEquals(

                "Expected status code "
                        + expectedStatus
                        + " but received "
                        + response.statusCode()
                        + ". Body: "
                        + response.asString(),

                expectedStatus,

                response.statusCode()
        );
    }





    @And("response time should be less than {int} ms")
    public void validateResponseTime(
            int expectedTime) {


        long actualTime =
                context
                        .getResponse()
                        .time();


        Assert.assertTrue(

                "Response time should be below "
                        + expectedTime
                        + " ms but was "
                        + actualTime
                        + " ms",

                actualTime < expectedTime
        );
    }





    @And("response header {string} should contain {string}")
    public void validateHeader(
            String headerName,
            String expectedValue) {


        String actualValue =
                context
                        .getResponse()
                        .getHeader(
                                headerName
                        );


        Assert.assertNotNull(

                headerName
                        + " header was not returned",

                actualValue
        );


        Assert.assertTrue(

                "Expected header "
                        + headerName
                        + " to contain "
                        + expectedValue
                        + " but received "
                        + actualValue,

                actualValue.contains(
                        expectedValue
                )
        );
    }



    @And("created employee data should be correct")
    public void validateCreatedEmployee() {


        Response response =
                context.getResponse();


        Assert.assertEquals(

                "Afaf",

                response
                        .jsonPath()
                        .getString(
                                "data.firstName"
                        )
        );


        Assert.assertEquals(

                "Alraddadi",

                response
                        .jsonPath()
                        .getString(
                                "data.lastName"
                        )
        );


        Assert.assertEquals(

                "Abdullah",

                response
                        .jsonPath()
                        .getString(
                                "data.middleName"
                        )
        );


        Assert.assertEquals(

                "1998-01-15",

                response
                        .jsonPath()
                        .getString(
                                "data.birthday"
                        )
        );


        Assert.assertNotNull(

                "employeeId should be returned",

                response
                        .jsonPath()
                        .get(
                                "data.employeeId"
                        )
        );



        Assert.assertTrue(

                response
                        .asString()
                        .contains(
                                "Afaf"
                        )
        );


        Assert.assertTrue(

                response
                        .asString()
                        .contains(
                                "Alraddadi"
                        )
        );


        Assert.assertTrue(

                response
                        .asString()
                        .contains(
                                "Abdullah"
                        )
        );
    }





    @And("employee number should be returned")
    public void employeeNumberReturned() {


        int empNumber =
                context
                        .getResponse()
                        .jsonPath()
                        .getInt(
                                "data.empNumber"
                        );


        Assert.assertTrue(

                "empNumber should be greater than zero",

                empNumber > 0
        );


        context.setEmpNumber(
                empNumber
        );
    }




    @And("employee data should match the assessment employee")
    public void validateEmployeeData() {


        Response response =
                context.getResponse();


        Assert.assertEquals(

                context.getEmpNumber(),

                response
                        .jsonPath()
                        .getInt(
                                "data.empNumber"
                        )
        );



        Assert.assertEquals(

                "Afaf",

                response
                        .jsonPath()
                        .getString(
                                "data.firstName"
                        )
        );



        Assert.assertEquals(

                "Alraddadi",

                response
                        .jsonPath()
                        .getString(
                                "data.lastName"
                        )
        );



        Assert.assertEquals(

                "Abdullah",

                response
                        .jsonPath()
                        .getString(
                                "data.middleName"
                        )
        );



        Assert.assertEquals(

                "1998-01-15",

                response
                        .jsonPath()
                        .getString(
                                "data.birthday"
                        )
        );



        Assert.assertNotNull(

                "employeeId should exist",

                response
                        .jsonPath()
                        .get(
                                "data.employeeId"
                        )
        );



        Object gender =
                response
                        .jsonPath()
                        .get(
                                "data.gender"
                        );


        Assert.assertNotNull(
                "gender should exist",
                gender
        );


        boolean validGender =
                gender.toString().equalsIgnoreCase("F")
                        ||
                        gender.toString().equals("2");


        Assert.assertTrue(

                "Expected gender F/2 but got "
                        + gender,

                validGender
        );
    }





    @And("updated employee first name should be {string}")
    public void updatedEmployeeFirstName(
            String expectedName) {


        Assert.assertEquals(

                expectedName,

                context
                        .getResponse()
                        .jsonPath()
                        .getString(
                                "data.firstName"
                        )
        );
    }


    @And("employee first name should be {string}")
    public void employeeFirstName(
            String expected) {


        Assert.assertEquals(

                expected,

                context
                        .getResponse()
                        .jsonPath()
                        .getString(
                                "data.firstName"
                        )
        );
    }



    @And("employee last name should be {string}")
    public void employeeLastName(
            String expected) {


        Assert.assertEquals(

                expected,

                context
                        .getResponse()
                        .jsonPath()
                        .getString(
                                "data.lastName"
                        )
        );
    }



    @And("employee middle name should be {string}")
    public void employeeMiddleName(
            String expected) {


        Assert.assertEquals(

                expected,

                context
                        .getResponse()
                        .jsonPath()
                        .getString(
                                "data.middleName"
                        )
        );
    }




    @And("employee job title should be {string}")
    public void employeeJobTitle(
            String expected) {


        Response response =
                context.getResponse();


        String title =
                response
                        .jsonPath()
                        .getString(
                                "data.jobTitle.title"
                        );


        if (title == null) {

            title =
                    response
                            .jsonPath()
                            .getString(
                                    "data.job_title"
                            );
        }


        Assert.assertEquals(

                "Job title does not match. Body: "
                        + response.asString(),

                expected,

                title
        );
    }





    private JSONObject employeeBody(
            String firstName) {


        JSONObject body =
                new JSONObject();


        body.put(
                "firstName",
                firstName
        );


        body.put(
                "lastName",
                "Alraddadi"
        );


        body.put(
                "middleName",
                "Abdullah"
        );


        body.put(
                "gender",
                "F"
        );


        body.put(
                "birthday",
                "1998-01-15"
        );


        body.put(
                "job_title",
                "SDET"
        );


        return body;
    }




    private String getToken(
            Response response) {


        String[] possiblePaths = {

                "token",

                "access_token",

                "accessToken",

                "jwt",

                "jwtToken",

                "data.token",

                "data.access_token",

                "data.accessToken",

                "data.jwt",

                "data.jwtToken"
        };


        for (String path :
                possiblePaths) {


            String value =
                    response
                            .jsonPath()
                            .getString(path);


            if (value != null
                    &&
                    !value.isBlank()
                    &&
                    !value.equalsIgnoreCase(
                            "null"
                    )) {


                return value;
            }
        }


        return null;
    }
}