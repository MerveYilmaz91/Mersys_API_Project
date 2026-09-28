package Campus.Tests.US107;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class US107_RemoveStudentFromGroup extends BaseTest {

    private static final String groupId = "6ab2ce10fe3620dd17504016";
    private static final String STUDENT_ID = "698487dbf31ebc2a530c7ee3";

    @Test(priority = 1)
    public void removeStudentFromGroup() {

        given().spec(request)
                .pathParam("groupId", groupId)
                .body(List.of(STUDENT_ID))
                .when()
                .post(Endpoints.REMOVE_STUDENTS)
                .then()
                .log().all()
                .statusCode(200);

        System.out.println("ÖĞRENCİ GRUPTAN ÇIKARILDI");
    }

    @Test(priority = 2, dependsOnMethods = "removeStudentFromGroup")
    public void checkStudentIsRemoved() {

        List<String> ids = given().spec(request)
                .pathParam("groupId", groupId)
                .queryParam("page", 0)
                .queryParam("size", 200)
                .when()
                .get(Endpoints.STUDENTS_OF_GROUP)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("content.id");

        Assert.assertFalse(ids.contains(STUDENT_ID),
                "Çıkarılan öğrenci hâlâ grubun listesinde (" + ids.size() + " üye)");
    }
}
