package Campus.Tests.US105;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US105_CreateStudentGroup extends BaseTest {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 5000;

    private static final String SCHOOL_ID = "68493da1aaaf023765b63bf6";

    public static String groupId;
    public static String groupName;

    @Test(priority = 1)
    public void createStudentGroup() {

        groupName = "TS Group " + System.currentTimeMillis();
        String groupDescription = "Group created by REST Assured test";

        Assert.assertTrue(groupName.length() <= MAX_NAME_LENGTH, "İsim 100 karakteri geçiyor");
        Assert.assertTrue(groupDescription.length() <= MAX_DESCRIPTION_LENGTH, "Açıklama 5000 karakteri geçiyor");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("schoolId", SCHOOL_ID);
        body.put("name", groupName);
        body.put("description", groupDescription);
        body.put("active", true);
        body.put("publicGroup", true);
        body.put("showToStudent", true);

        Response response =
                given().spec(request)
                        .body(body)
                        .when()
                        .post(Endpoints.STUDENT_GROUP)
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .response();

        groupId = response.jsonPath().getString("id");
        String storedName = response.jsonPath().getString("name");
        String storedDescription = response.jsonPath().getString("description");

        Assert.assertNotNull(groupId, "Cevapta grup ID değeri yok");
        Assert.assertEquals(storedName, groupName);
        Assert.assertEquals(storedDescription, groupDescription);
        Assert.assertTrue(storedName.length() <= MAX_NAME_LENGTH);
        Assert.assertTrue(storedDescription.length() <= MAX_DESCRIPTION_LENGTH);

        System.out.println("GRUP OLUŞTURULDU: " + groupId);
    }

    @Test(priority = 2)
    public void createGroupNameOver100() {

        String tooLongName = "B".repeat(MAX_NAME_LENGTH + 1);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("schoolId", SCHOOL_ID);
        body.put("name", tooLongName);
        body.put("description", "Name is too long");
        body.put("active", true);
        body.put("publicGroup", true);
        body.put("showToStudent", true);

        Response response =
                given().spec(request)
                        .body(body)
                        .when()
                        .post(Endpoints.STUDENT_GROUP)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        String storedName = response.jsonPath().getString("name");
        int storedLength = storedName == null ? 0 : storedName.length();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(response.statusCode(), 201, "Status kodu");
        soft.assertTrue(storedLength <= MAX_NAME_LENGTH,
                "AC105 ihlali: isim en fazla 100 karakter olmalı, API " + storedLength + " karakter kaydetti");
        soft.assertAll();
    }

    @Test(priority = 3)
    public void createGroupDescriptionOver5000() {

        String tooLongDescription = "G".repeat(MAX_DESCRIPTION_LENGTH + 2);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("schoolId", SCHOOL_ID);
        body.put("name", "TS Long Description GroupP");
        body.put("description", tooLongDescription);
        body.put("active", true);
        body.put("publicGroup", true);
        body.put("showToStudent", true);

        Response response =
                given().spec(request)
                        .body(body)
                        .when()
                        .post(Endpoints.STUDENT_GROUP)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        String storedDescription = response.jsonPath().getString("description");
        int storedLength = storedDescription == null ? 0 : storedDescription.length();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(response.statusCode(), 201, "Status kodu");
        soft.assertTrue(storedLength <= MAX_DESCRIPTION_LENGTH,
                "AC105 ihlali: açıklama en fazla 5000 karakter olmalı, API " + storedLength + " karakter kaydetti");
        soft.assertAll();
    }
}
