package Campus.Tests.US002;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class US002_FeeType extends BaseTest {

    public static String feeTypeId;

    // CREATE
    @Test(priority = 1)
    public void createFeeType() {

        String unique = String.valueOf(System.currentTimeMillis());

        String feeName = "Merve Fee " + unique;
        String feeCode = "MF" + unique.substring(unique.length() - 6);

        // Priority de benzersiz olsun
        int priority = (int) (System.currentTimeMillis() % 100000);

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", null);
        body.put("name", feeName);
        body.put("code", feeCode);
        body.put(
                "budgetAccountIntegrationCode",
                "MFIN" + unique.substring(unique.length() - 6)
        );
        body.put("priority", priority);
        body.put("active", true);
        body.put("translateName", new Object[]{});

        Response response =
                request
                        .body(body)
                        .when()
                        .post(Endpoints.FEE_TYPES);

        System.out.println("CREATE STATUS: " + response.statusCode());
        System.out.println("CREATE RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 201);

        feeTypeId = response.jsonPath().getString("id");

        Assert.assertNotNull(feeTypeId, "Fee Type ID alınamadı!");
        Assert.assertEquals(response.jsonPath().getString("name"), feeName);
        Assert.assertEquals(response.jsonPath().getString("code"), feeCode);
        Assert.assertEquals(response.jsonPath().getInt("priority"), priority);
        Assert.assertTrue(response.jsonPath().getBoolean("active"));

        System.out.println("Fee Type oluşturuldu. ID: " + feeTypeId);
    }


    // EDIT
    @Test(priority = 2, dependsOnMethods = "createFeeType")
    public void editFeeType() {

        String unique = String.valueOf(System.currentTimeMillis());

        String updatedName = "Merve Fee Updated " + unique;
        String updatedCode =
                "MU" + unique.substring(unique.length() - 6);

        int updatedPriority =
                (int) (System.currentTimeMillis() % 100000) + 1;

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", feeTypeId);
        body.put("name", updatedName);
        body.put("code", updatedCode);
        body.put(
                "budgetAccountIntegrationCode",
                "MUIN" + unique.substring(unique.length() - 6)
        );
        body.put("priority", updatedPriority);
        body.put("active", true);
        body.put("translateName", new Object[]{});

        Response response =
                request
                        .body(body)
                        .when()
                        .put(Endpoints.FEE_TYPES);

        System.out.println("EDIT STATUS: " + response.statusCode());
        System.out.println("EDIT RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getString("id"),
                feeTypeId
        );

        Assert.assertEquals(
                response.jsonPath().getString("name"),
                updatedName
        );

        Assert.assertEquals(
                response.jsonPath().getString("code"),
                updatedCode
        );

        Assert.assertEquals(
                response.jsonPath().getInt("priority"),
                updatedPriority
        );

        Assert.assertTrue(
                response.jsonPath().getBoolean("active")
        );

        System.out.println(
                "Fee Type başarıyla güncellendi. ID: " + feeTypeId
        );
    }


    // DELETE
    @Test(priority = 3, dependsOnMethods = "editFeeType")
    public void deleteFeeType() {

        Response response =
                request
                        .when()
                        .delete(Endpoints.FEE_TYPES + "/" + feeTypeId);

        System.out.println("DELETE STATUS: " + response.statusCode());
        System.out.println("DELETE RESPONSE: " + response.asPrettyString());

        Assert.assertTrue(
                response.statusCode() == 200 ||
                        response.statusCode() == 204
        );

        System.out.println(
                "Fee Type başarıyla silindi. ID: " + feeTypeId
        );
    }
}