package Campus.Tests.US105;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US105_EditStudentGroup extends BaseTest {

    private static final String SCHOOL_ID = "68493da1aaaf023765b63bf6";

    private static final String groupId = "";

    @Test
    public void editStudentGroup() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("id", groupId);
        body.put("schoolId", SCHOOL_ID);
        body.put("name", "TS Group " + System.currentTimeMillis() + " Edited");
        body.put("description", "Group updated by REST Assured test");
        body.put("active", true);
        body.put("publicGroup", false);
        body.put("showToStudent", false);

        Response response =
                given().spec(request)
                        .body(body)
                        .when()
                        .put(Endpoints.STUDENT_GROUP)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        for (String key : List.of("id", "name", "description", "active", "publicGroup", "showToStudent")) {
            Assert.assertEquals(response.jsonPath().get(key), body.get(key), key + " alanı");
        }

        System.out.println("GRUP DÜZENLENDİ");
    }
}
