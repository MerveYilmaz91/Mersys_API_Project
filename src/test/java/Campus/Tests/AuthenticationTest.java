package Campus.Tests;

import Campus.Utilities.Authentication;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationTest {

    @Test
    public void loginTest() {

        Authentication.getToken();

        Assert.assertNotNull(Authentication.token);

        System.out.println("LOGIN BAŞARILI");
    }
}
