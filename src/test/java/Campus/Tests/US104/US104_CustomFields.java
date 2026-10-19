package Campus.Tests.US104;
import Campus.Utilities.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class US104_CustomFields extends BaseTest {

    String entityFieldId;

    @Test
    public void createEntityFieldTest() {

        String uniqueName = "SDET Test Alanı " + System.currentTimeMillis();

        String uniqueCode = "CODE_" + (System.currentTimeMillis() % 100000);
        Map<String, Object> fieldBody = new HashMap<>();

        fieldBody.put("id", null);
        fieldBody.put("name", uniqueName);
        fieldBody.put("translateName", new ArrayList<>());
        fieldBody.put("code", uniqueCode);
        fieldBody.put("type", "BOOLEAN");
        fieldBody.put("systemField", false);
        fieldBody.put("constant", true);
        fieldBody.put("customParameter", null);
        fieldBody.put("defaultValue", null);
        fieldBody.put("hint", null);
        fieldBody.put("listSource", null);
        fieldBody.put("mask", null);
        fieldBody.put("max", null);
        fieldBody.put("maxLength", 0);
        fieldBody.put("min", null);
        fieldBody.put("multiple", false);
        fieldBody.put("offLabel", "");
        fieldBody.put("onLabel", "");
        fieldBody.put("prefix", null);
        fieldBody.put("rows", 2);
        fieldBody.put("systemFieldName", null);
        fieldBody.put("tenantId", "684938be65c54ff057e33547");

        Response response =
                given()
                        .spec(request)
                        .body(fieldBody)
                        .when()
                        .post("/school-service/api/entity-field")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        entityFieldId = response.jsonPath().getString("id");

        System.out.println("Başarıyla oluşturuldu!" + uniqueName);
        System.out.println("Oluşturulan ID: " + entityFieldId);
    }

    @Test(dependsOnMethods = "createEntityFieldTest")
    public void listEntityFieldTest() {

            Map<String, Object> searchBody = new HashMap<>();
            searchBody.put("tenantId", "684938be65c54ff057e33547");

            given()
                    .spec(request)
                    .body(searchBody)
                    .when()
                    .post("/school-service/api/entity-field/search")
                    .then()
                    .statusCode(200);

            System.out.println("Başarıyla listelendi!");
        }

    @Test(dependsOnMethods = "createEntityFieldTest")
    public void updateEntityFieldTest() {

        Map<String, Object> updateBody = new HashMap<>();

        updateBody.put("id", entityFieldId);
        updateBody.put("name", "SDET Test Alanı - GÜNCELLENDİ");

        updateBody.put("translateName", new ArrayList<>());
        updateBody.put("code", "CODE_UPD_" + (System.currentTimeMillis() % 100000));
        updateBody.put("type", "BOOLEAN");
        updateBody.put("systemField", false);
        updateBody.put("constant", true);
        updateBody.put("customParameter", null);
        updateBody.put("defaultValue", null);
        updateBody.put("hint", null);
        updateBody.put("listSource", null);
        updateBody.put("mask", null);
        updateBody.put("max", null);
        updateBody.put("maxLength", 0);
        updateBody.put("min", null);
        updateBody.put("multiple", false);
        updateBody.put("offLabel", "");
        updateBody.put("onLabel", "");
        updateBody.put("prefix", null);
        updateBody.put("rows", 2);
        updateBody.put("systemFieldName", null);
        updateBody.put("tenantId", "684938be65c54ff057e33547");

        given()
                .spec(request)
                .body(updateBody)
                .when()
                .put("/school-service/api/entity-field")
                .then()
                .statusCode(200);

        System.out.println("Başarıyla güncellendi!");
    }

    @Test(dependsOnMethods = "updateEntityFieldTest")
    public void deleteEntityFieldTest() {

        given()
                .spec(request)
                .when()
                .delete("/school-service/api/entity-field/" + entityFieldId)
                .then()
                .statusCode(204);

        System.out.println("Başarıyla silindi!");
    }
}


