package Campus.Tests.US106;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class US106_GetStudentID extends BaseTest {

    private static final String groupId = "";

    @Test
    public void getStudentGroupById() {

        Response response =
                given().spec(request)
                        .pathParam("groupId", groupId)
                        .when()
                        .get(Endpoints.STUDENT_GROUP_BY_ID)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertEquals(response.jsonPath().getString("id"), groupId, "Dönen grup, istenen grup olmalı");
    }
}
