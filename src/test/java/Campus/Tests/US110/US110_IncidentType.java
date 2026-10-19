package Campus.Tests.US110;

import Campus.Utilities.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US110_IncidentType extends BaseTest {

    String incidentTypeId;

    @Test
    public void createIncidentTypeTest() {

        System.out.println("US110 - CREATE TESTİ BAŞLIYOR...");
        String uniqueName = "SDET Olay Türü " + System.currentTimeMillis();

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", null);
        reqBody.put("name", uniqueName);
        reqBody.put("academicBased", false);
        reqBody.put("active", false);
        reqBody.put("enabled", false);
        reqBody.put("maxPoint", 50);
        reqBody.put("minPoint", 10);
        reqBody.put("notifyResolutionWithEmail", false);
        reqBody.put("notifyWithEmail", false);
        reqBody.put("notifyWithMessage", false);
        reqBody.put("permissions", new ArrayList<>());
        reqBody.put("tenantId", "684938be65c54ff057e33547");

        Response response =
                given()
                        .spec(request)
                        .body(reqBody)
                        .when()
                        .post("/school-service/api/incident-type")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        incidentTypeId = response.jsonPath().getString("id");

        System.out.println("Başarıyla oluşturuldu! Alınan ID: " + incidentTypeId);
    }

    @Test(dependsOnMethods = "createIncidentTypeTest")
    public void updateIncidentTypeTest() {

        System.out.println("US110 - Güncellenecek ID: " + incidentTypeId);
        String updatedUniqueName = "SDET Olay Türü GÜNCEL " + System.currentTimeMillis();

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("id", incidentTypeId);
        updateBody.put("name", updatedUniqueName);
        updateBody.put("academicBased", false);
        updateBody.put("active", true);
        updateBody.put("enabled", true);
        updateBody.put("maxPoint", 100);
        updateBody.put("minPoint", 20);
        updateBody.put("notifyResolutionWithEmail", false);
        updateBody.put("notifyWithEmail", false);
        updateBody.put("notifyWithMessage", false);
        updateBody.put("permissions", new ArrayList<>());
        updateBody.put("tenantId", "684938be65c54ff057e33547");

        given()
                .spec(request)
                .body(updateBody)
                .when()
                .put("/school-service/api/incident-type")
                .then()
                .statusCode(200);

        System.out.println("Başarıyla güncellendi! Yeni İsim: " + updatedUniqueName);
    }

    @Test(dependsOnMethods = "updateIncidentTypeTest")
    public void deleteIncidentTypeTest() {

        System.out.println("US110 - Silinecek ID: " + incidentTypeId);

        given()
                .spec(request)
                .when()
                .delete("/school-service/api/incident-type/" + incidentTypeId)
                .then()
                .statusCode(anyOf(is(200), is(204)));
        System.out.println("Başarıyla silindi!");
    }
}

