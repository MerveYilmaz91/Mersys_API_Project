package Campus.Tests.US103;
import Campus.Utilities.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class US103_ExamsTest extends BaseTest {

    String examId;

    @Test
    public void createExamTest() {

        Map<String, Object> examBody = new HashMap<>();
        examBody.put("school", "68493da1aaaf023765b63bf6");
        examBody.put("name", "SDET Sınavı");
        examBody.put("academicPeriod", "684a643560f1bf39a1ecbd71");

        Map<String, String> gradeLevelMap = new HashMap<>();
        gradeLevelMap.put("id", "684a64c8c64172c77f1a9e3e");
        examBody.put("gradeLevel", gradeLevelMap);

        Response response =
                given()
                        .spec(request)
                        .body(examBody)
                        .log().all()
                        .when()
                        .post("/school-service/api/exams")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract()
                        .response();

        examId = response.jsonPath().getString("id");

        System.out.println("Oluşturulan Sınavın ID'si: " + examId);
    }

    @Test(dependsOnMethods = "createExamTest")
    public void updateExamTest() {

        Map<String, Object> updateBody = new HashMap<>();

        updateBody.put("id", examId);

        updateBody.put("name", "SDET Sınavı **Güncellendi** ");
        updateBody.put("academicPeriod", "684a643560f1bf39a1ecbd71");
        updateBody.put("school", "68493da1aaaf023765b63bf6");

        Map<String, String> gradeLevelMap = new HashMap<>();
        gradeLevelMap.put("id", "684a64c8c64172c77f1a9e3e");
        updateBody.put("gradeLevel", gradeLevelMap);

        given()
                .spec(request)
                .body(updateBody)
                .when()
                .put("/school-service/api/exams")
                .then()
                .statusCode(200);

        System.out.println("Sınav başarıyla güncellendi!");
    }

    @Test(dependsOnMethods = "updateExamTest")
    public void deleteExamTest() {

        given()
                .spec(request)
                .when()
                .delete("/school-service/api/exams/" + examId)
                .then()
                .statusCode(204);

        System.out.println("Sınav ilk seferde başarıyla silindi!");
    }

    @Test(dependsOnMethods = "deleteExamTest")
    public void deleteExamNegativeTest() {

        System.out.println("Silinmiş ID ile tekrar silme deneniyor.");

        given()
                .spec(request)
                .log().uri()
                .when()
                .delete("/school-service/api/exams/" + examId)
                .then()
                .log().status()
                .statusCode(404);
    }
}




