package Campus.Utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {

    public static String token;

    public static void getToken() {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("username", ConfigReader.getProperty("username"));
        loginBody.put("password", ConfigReader.getProperty("password"));

        Response response =
                given()
                        .contentType("application/json")
                        .body(loginBody)
                        .when()
                        .post(Endpoints.LOGIN)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        token = response.jsonPath().getString("access_token");

        System.out.println("Token başarıyla alındı.");
    }
}
