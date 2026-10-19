package Campus.Tests.US108;
import Campus.Utilities.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;


public class US108_EducationStandart extends BaseTest {


    String educationStandardId;

    @Test
    public void createEducationStandardTest() {

        Map<String, Object> body = new HashMap<>();

        body.put("name", "SDET Eğitim Standardı " + System.currentTimeMillis());
        body.put("description", "Bu standart otomasyon testleri için Postman üzerinden oluşturulmuştur.");
        body.put("schoolId", "68493da1aaaf023765b63bf6");
        body.put("subjectId", "68493da1aaaf023765b63bf7");
        body.put("gradeLevelId", "68493da1aaaf023765b63bf8");

        Response response =
        given()
                .spec(request)
                .body(body)
                .when()
                .post("/school-service/api/education-standard")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();

               educationStandardId = response.jsonPath().getString("id");

    }

    @Test(dependsOnMethods = "createEducationStandardTest")
    public void updateEducationStandardTest() {

        Map<String, Object> updateBody = new HashMap<>();

        updateBody.put("id", educationStandardId);
        updateBody.put("name", "SDET Eğitim Standardı GÜNCEL " + System.currentTimeMillis());
        updateBody.put("description", "Bu standart otomasyon testleri için güncellendi.");
        updateBody.put("schoolId", "68493da1aaaf023765b63bf6");
        updateBody.put("subjectId", "68493da1aaaf023765b63bf7");
        updateBody.put("gradeLevelId", "68493da1aaaf023765b63bf8");

        given()
                .spec(request)
                .body(updateBody)
                .when()
                .log().all()
                .put("/school-service/api/education-standard")
                .then()
                .log().body()
                .statusCode(200);

        System.out.println("US108 Başarıyla güncellendi!");
    }

    @Test(dependsOnMethods = "updateEducationStandardTest")
    public void deleteEducationStandardTest() {

        given()
                .spec(request)
                .when()
                .delete("/school-service/api/education-standard/" + educationStandardId)
                .then()
                .statusCode(204);

        System.out.println("US108 Başarıyla silindi!");
    }
}

