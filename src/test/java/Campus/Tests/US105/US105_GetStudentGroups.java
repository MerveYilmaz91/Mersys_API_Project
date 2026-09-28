package Campus.Tests.US105;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US105_GetStudentGroups extends BaseTest {

    private static final String SCHOOL_ID = "68493da1aaaf023765b63bf6";

    @Test
    public void listStudentGroups() {

        Response response =
                given().spec(request)
                        .pathParam("schoolId", SCHOOL_ID)
                        .when()
                        .get(Endpoints.STUDENT_GROUP_BY_SCHOOL)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        List<Map<String, Object>> groups = response.jsonPath().getList("$");

        Assert.assertNotNull(groups, "Cevap bir liste değil");

        for (Map<String, Object> group : groups) {
            Assert.assertTrue(group.get("id") instanceof String, "id metin olmalı");
            Assert.assertTrue(group.get("name") instanceof String, "name metin olmalı");
            Assert.assertEquals(group.get("schoolId"), SCHOOL_ID, "Başka okulun grubu listede");
        }

        System.out.println("GRUP LİSTESİ BAŞARILI");
    }
}
