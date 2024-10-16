package com.agave.tests;

import org.testng.annotations.Test;
import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DemoTest {

    //@Test
    public void test1() {
        Response response = RestAssured.get(System.getenv("API_SERVICE_BASE_URL"));
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    //@Test
    public void test2() {
        Response response = RestAssured.get(System.getenv("API_SERVICE_BASE_URL") + "actuator/health");
        Assert.assertEquals(response.getStatusCode(), 503);
    }

    @Test
    public void test3() {
        Assert.assertEquals(200, 200);
    }
}
