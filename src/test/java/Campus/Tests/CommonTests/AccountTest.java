package Campus.Tests.CommonTests;

import Campus.Utilities.BaseTest;
import Campus.Utilities.Endpoints;
import org.testng.annotations.Test;

public class AccountTest extends BaseTest {

    @Test
    public void getAccountTest() {

        request
                .when()
                .get(Endpoints.ACCOUNT)
                .then()
                .statusCode(200);

        System.out.println("ACCOUNT TEST BAŞARILI");
    }
}
