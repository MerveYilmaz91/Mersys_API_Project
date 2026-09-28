package Campus.Tests.US106;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class US106_AddStudentToGroup extends BaseTest {

    private static final String groupId = "6ab2ce10fe3620dd17504016";
    private static final String STUDENT_ID = "698487dbf31ebc2a530c7ee3";

    @Test(priority = 1)
    public void addStudentToGroup() {

        given().spec(request)
                .pathParam("groupId", groupId)
                .body(List.of(STUDENT_ID))
                .when()
                .post(Endpoints.ADD_STUDENTS)
                .then()
                .log().all()
                .statusCode(200);

        System.out.println("ÖĞRENCİ GRUBA EKLENDİ");
    }

    @Test(priority = 2, dependsOnMethods = "addStudentToGroup")
    public void checkStudentIsInGroup() {

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

        Assert.assertTrue(ids.contains(STUDENT_ID),
                "Eklenen öğrenci grubun listesinde yok (" + ids.size() + " üye)");
    }
}
