package org.context;

import io.restassured.response.Response;

public class TestContext {

    private String token;

    private int empNumber;

    private Response response;


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public int getEmpNumber() {
        return empNumber;
    }


    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }


    public Response getResponse() {
        return response;
    }


    public void setResponse(Response response) {
        this.response = response;
    }
}
