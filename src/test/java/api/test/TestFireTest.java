package api.test;

import api.endpoints.TestFireEndpoints;
import api.reports.ExternalReport;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;

public class TestFireTest extends ExternalReport {

    @Test(priority = 1)
    public void createToken() throws FileNotFoundException {
        TestFireEndpoints.createToken();
    }

    @Test(priority = 2, dependsOnMethods = "createToken")
    public void verifyLogin() throws FileNotFoundException {
        TestFireEndpoints.verifyLogin();

    }

    @Test(priority = 14, dependsOnMethods = "createToken")
    public void verifyLoginWithInvalidCredentials() throws FileNotFoundException {
        TestFireEndpoints.createTokenWithInvalidCredentials();

    }

    @Test(priority = 13, dependsOnMethods = "createToken")
    public void verifyAccountWithInvalidAccountDetails() throws FileNotFoundException {
        TestFireEndpoints.verifyAccountWithInvalidAccountDetails();

    }

    @Test(priority = 3, dependsOnMethods = "verifyLogin")
    public void listUserAccounts() throws FileNotFoundException {
        TestFireEndpoints.listUserAccounts();

    }

    @Test(priority = 4, dependsOnMethods = "listUserAccounts")
    public void getSavingsAccountDetails() throws FileNotFoundException {
        TestFireEndpoints.getSavingsAccountDetails();

    }

    @Test(priority = 4, dependsOnMethods = "listUserAccounts")
    public void getCheckingAccountDetails() throws FileNotFoundException {
        TestFireEndpoints.getCheckingAccountDetails();
    }

    @Test(priority = 4, dependsOnMethods = "listUserAccounts")
    public void getCreditCardAccountDetails() throws FileNotFoundException {
        TestFireEndpoints.getCreditCardAccountDetails();
    }

    @Test(priority = 5, dependsOnMethods = "verifyLogin")
    public void transferFunds() throws FileNotFoundException {
        TestFireEndpoints.transferFunds();
    }

    @Test(priority = 18, dependsOnMethods = "verifyLogin")
    public void logout() throws FileNotFoundException {
        TestFireEndpoints.logout();
    }
}