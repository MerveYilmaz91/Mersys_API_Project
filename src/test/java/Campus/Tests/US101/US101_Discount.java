package Campus.Tests.US101;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class US101_Discount extends BaseTest {

    public static String discountId;

    @Test(priority = 1)
    public void createDiscount() {

        String unique = String.valueOf(System.currentTimeMillis());

        String description = "Merve Discount " + unique;
        String code = "MD" + unique.substring(unique.length() - 6);

        int priority = (int) (System.currentTimeMillis() % 100000);

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", null);
        body.put("description", description);
        body.put("code", code);
        body.put("priority", priority);
        body.put("active", true);
        body.put("translateDescription", new Object[]{});

        Response response =
                request
                        .body(body)
                        .when()
                        .post(Endpoints.DISCOUNTS);

        System.out.println("CREATE STATUS: " + response.statusCode());
        System.out.println("CREATE RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 201);

        discountId = response.jsonPath().getString("id");

        Assert.assertNotNull(
                discountId,
                "Discount ID alınamadı!"
        );

        Assert.assertEquals(
                response.jsonPath().getString("description"),
                description
        );

        Assert.assertEquals(
                response.jsonPath().getString("code"),
                code
        );

        Assert.assertEquals(
                response.jsonPath().getInt("priority"),
                priority
        );

        Assert.assertTrue(
                response.jsonPath().getBoolean("active")
        );

        System.out.println(
                "Discount oluşturuldu. ID: " + discountId
        );
    }
    @Test(priority = 2, dependsOnMethods = "createDiscount")
    public void editDiscount() {

        String unique = String.valueOf(System.currentTimeMillis());

        String updatedDescription = "Merve Discount Updated " + unique;
        String updatedCode = "MDU" + unique.substring(unique.length() - 6);

        int updatedPriority =
                (int) (System.currentTimeMillis() % 100000);

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", discountId);
        body.put("description", updatedDescription);
        body.put("code", updatedCode);
        body.put("priority", updatedPriority);
        body.put("active", true);
        body.put("translateDescription", new Object[]{});

        Response response =
                request
                        .body(body)
                        .when()
                        .put(Endpoints.DISCOUNTS);

        System.out.println("EDIT STATUS: " + response.statusCode());
        System.out.println("EDIT RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getString("id"),
                discountId
        );

        Assert.assertEquals(
                response.jsonPath().getString("description"),
                updatedDescription
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
                "Discount başarıyla güncellendi. ID: " + discountId
        );
    }
    @Test(priority = 3, dependsOnMethods = "editDiscount")
    public void deleteDiscount() {

        Response response =
                request
                        .when()
                        .delete(Endpoints.DISCOUNTS + "/" + discountId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        System.out.println("DELETE STATUS: " + response.statusCode());
        System.out.println("Discount başarıyla silindi. ID: " + discountId);
    }
}
