package Campus.Tests.US105;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class US105_DeleteStudentGroup extends BaseTest {

    private static final String groupId = "6ab2cc65fe3620dd17504013";

    @Test(priority = 1)
    public void deleteStudentGroup() {

        Response response =
                given().spec(request)
                        .pathParam("groupId", groupId)
                        .when()
                        .delete(Endpoints.STUDENT_GROUP_BY_ID)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertTrue(response.getBody().asString().isEmpty(), "Silme cevabının gövdesi boş olmalı");

        System.out.println("GRUP SİLİNDİ");
    }

    @Test(priority = 2, dependsOnMethods = "deleteStudentGroup")
    public void deleteAlreadyDeletedGroup() {

        given().spec(request)
                .pathParam("groupId", groupId)
                .when()
                .delete(Endpoints.STUDENT_GROUP_BY_ID)
                .then()
                .log().all()
                .statusCode(400);
    }
}
