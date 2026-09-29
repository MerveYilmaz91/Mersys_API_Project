package Campus.Tests.US001;

import Campus.Utilities.ConfigReader;
import Campus.Utilities.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US001_Login {

    @Test(priority = 1)
    public void positiveLogin() {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Map<String, Object> loginBody = new HashMap<>();
        loginBody.put("username", ConfigReader.getProperty("username"));
        loginBody.put("password", ConfigReader.getProperty("password"));
        loginBody.put("rememberMe", true);

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

        String accessToken = response.jsonPath().getString("access_token");

        Assert.assertNotNull(accessToken, "Access token alınamadı!");

        System.out.println("Positive Login başarılı.");
    }

    @Test(priority = 2)
    public void negativeLoginWrongPassword() {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Map<String, Object> loginBody = new HashMap<>();
        loginBody.put("username", ConfigReader.getProperty("username"));
        loginBody.put("password", "yanlis123");
        loginBody.put("rememberMe", true);

        given()
                .contentType("application/json")
                .body(loginBody)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .statusCode(400);

        System.out.println("Wrong Password testi başarılı.");
    }
    @Test(priority = 3)
    public void negativeLoginMissingPassword() {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Map<String, Object> loginBody = new HashMap<>();
        loginBody.put("username", ConfigReader.getProperty("username"));
        loginBody.put("rememberMe", true);

        given()
                .contentType("application/json")
                .body(loginBody)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .statusCode(400);

        System.out.println("Missing Password testi başarılı.");
    }
}
