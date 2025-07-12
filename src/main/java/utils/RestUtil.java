package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtil {
    public static Response get(){
        RequestSpecification specification= RestAssured.given();
        Response response=specification.request().get();
        return response;
    }
}
