package Campus.Tests.US102;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class US102_BankAccount extends BaseTest {

    String bankAccountId;
    String iban;
    String integrationCode;

    @Test(priority = 1)
    public void createBankAccount() {

        String unique = String.valueOf(System.currentTimeMillis());

        String bankName = "Merve Bank " + unique;
        integrationCode = "MBANK" + unique.substring(unique.length() - 6);

        // Her çalıştırmada farklı IBAN oluşsun
        String digits = unique.replaceAll("\\D", "");
        iban = "TR" + "10" + digits;

        // IBAN uzunluğunu 26 karaktere tamamla
        while (iban.length() < 26) {
            iban = iban + "0";
        }

        // 26 karakterden uzunsa kes
        if (iban.length() > 26) {
            iban = iban.substring(0, 26);
        }

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", null);
        body.put("name", bankName);
        body.put("iban", iban);
        body.put("currency", "TRY");
        body.put("integrationCode", integrationCode);
        body.put("active", true);
        body.put("schoolId", "68493da1aaaf023765b63bf6");

        Response response =
                request
                        .body(body)
                        .when()
                        .post(Endpoints.BANK_ACCOUNTS)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        System.out.println("CREATE STATUS: " + response.statusCode());
        System.out.println("CREATE RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 201);

        bankAccountId = response.jsonPath().getString("id");

        Assert.assertNotNull(bankAccountId);
        Assert.assertEquals(
                response.jsonPath().getString("name"),
                bankName
        );
        Assert.assertEquals(
                response.jsonPath().getString("iban"),
                iban
        );
        Assert.assertEquals(
                response.jsonPath().getString("currency"),
                "TRY"
        );
        Assert.assertEquals(
                response.jsonPath().getString("integrationCode"),
                integrationCode
        );
        Assert.assertTrue(
                response.jsonPath().getBoolean("active")
        );

        System.out.println(
                "Bank Account oluşturuldu. ID: " + bankAccountId
        );
    }


    @Test(priority = 2, dependsOnMethods = "createBankAccount")
    public void editBankAccount() {

        String updatedName = "Merve Bank Updated";

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("id", bankAccountId);
        body.put("name", updatedName);

        // CREATE sırasında oluşan aynı IBAN'ı kullanıyoruz
        body.put("iban", iban);

        body.put("currency", "TRY");

        // CREATE sırasında oluşan aynı integrationCode
        body.put("integrationCode", integrationCode);

        body.put("active", true);
        body.put("schoolId", "68493da1aaaf023765b63bf6");

        Response response =
                request
                        .body(body)
                        .when()
                        .put(Endpoints.BANK_ACCOUNTS)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        System.out.println("EDIT STATUS: " + response.statusCode());
        System.out.println("EDIT RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getString("id"),
                bankAccountId
        );

        Assert.assertEquals(
                response.jsonPath().getString("name"),
                updatedName
        );

        Assert.assertEquals(
                response.jsonPath().getString("iban"),
                iban
        );

        Assert.assertEquals(
                response.jsonPath().getString("currency"),
                "TRY"
        );

        Assert.assertEquals(
                response.jsonPath().getString("integrationCode"),
                integrationCode
        );

        Assert.assertTrue(
                response.jsonPath().getBoolean("active")
        );

        System.out.println(
                "Bank Account başarıyla güncellendi. ID: " + bankAccountId
        );
    }


    @Test(priority = 3, dependsOnMethods = "editBankAccount")
    public void deleteBankAccount() {

        Response response =
                request
                        .when()
                        .delete(Endpoints.BANK_ACCOUNTS + "/" + bankAccountId)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        System.out.println("DELETE STATUS: " + response.statusCode());
        System.out.println("DELETE RESPONSE: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);

        System.out.println(
                "Bank Account başarıyla silindi. ID: " + bankAccountId
        );
    }
}