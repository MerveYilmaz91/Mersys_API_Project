package Campus.Utilities;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected RequestSpecification request;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Authentication.getToken();

        request = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + Authentication.token);
    }
}