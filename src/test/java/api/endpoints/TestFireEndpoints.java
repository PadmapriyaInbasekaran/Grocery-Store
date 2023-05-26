package api.endpoints;

import api.payload.TestFirePOJO;
import api.reports.ExternalReport;
import api.utilities.PropertiesClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TestFireEndpoints extends ExternalReport {
    public static RequestLoggingFilter requestLoggingFilter;
    public static ResponseLoggingFilter responseLoggingFilter;
    public static String postResponse;
    public static String responseForSavingsAccount;
    public static String responseForCheckingAccount;
    public static String responseForCreditCardAccount;
    public static TestFirePOJO data;
    static Properties locationPath = PropertiesClass.readProperty("src\\test\\java\\api\\utilities\\testData.properties");

    public static String createToken() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream("logs\\createToken.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new TestFirePOJO();
        data.setUsername(locationPath.getProperty("username"));
        data.setPassword(locationPath.getProperty("password"));
        postResponse = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .body(data)
                .when()
                .post(Constants.post_url).jsonPath().getString("Authorization");
        test.log(LogStatus.INFO, "Token generated successfully!",postResponse);
        return postResponse;
    }

    public static Response verifyLogin() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\verifyLogin.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .when()
                .auth().oauth2(postResponse)
                .get(Constants.get_url);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        test.log(LogStatus.INFO, "User created successfully!");
        test.log(LogStatus.INFO, "User logged in successfully!");
        return response;
    }

    public static void listUserAccounts() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\listUserAccounts.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        responseForSavingsAccount = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url).jsonPath().getString("Accounts.find { it.Name == 'Savings' }.id");
        responseForCheckingAccount = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url).jsonPath().getString("Accounts.find { it.Name == 'Checking' }.id");
        responseForCreditCardAccount = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url).jsonPath().getString("Accounts.find { it.Name == 'Credit Card' }.id");
        test.log(LogStatus.INFO, "Listed all user accounts successfully!");

    }

    public static Response getSavingsAccountDetails() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\getSavingsAccountDetails.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);

        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .pathParam("accountNo", responseForSavingsAccount)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url + "/{accountNo}");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        test.log(LogStatus.INFO, "Savings account details displayed successfully!");
        return response;
    }

    public static Response getCheckingAccountDetails() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\getCheckingAccountDetails.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);

        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .pathParam("accountNo", responseForCheckingAccount)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url + "/{accountNo}");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        test.log(LogStatus.INFO, "Checking account details displayed successfully!");
        return response;
    }

    public static Response getCreditCardAccountDetails() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\getCreditCardAccountDetails.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);

        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .pathParam("accountNo", responseForCreditCardAccount)
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url + "/{accountNo}");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        test.log(LogStatus.INFO, "Credit card account details displayed successfully!");
        return response;
    }

    public static void transferFunds() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\transferFunds.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data.setToAccount(responseForCheckingAccount);
        data.setFromAccount(responseForSavingsAccount);
        data.setTransferAmount(locationPath.getProperty("transferAmount"));
        String transferStatus = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .body(data)
                .when()
                .header("Authorization", postResponse)
                .post(Constants.transferFunds_url).jsonPath().getString("success");
        Assert.assertEquals(transferStatus.contains("successfully transferred"), true);
        test.log(LogStatus.INFO, "Funds transferred successfully!");
    }

    public static void logout() throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream("logs\\logout.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, false);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        String logoutStatus = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .when()
                .get(Constants.logout_url).jsonPath().getString("LoggedOut");
        Assert.assertEquals(logoutStatus.contains("True"), true);
        test.log(LogStatus.INFO, "Logged out successfully!");
    }

    public static String createTokenWithInvalidCredentials() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream("logs\\createTokenWithInvalidCredentials.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new TestFirePOJO();
        data.setUsername(locationPath.getProperty("invalidUsername"));
        data.setPassword(locationPath.getProperty("invalidPassword"));
        postResponse = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .body(data)
                .when()
                .post(Constants.post_url).jsonPath().getString("error");
        Assert.assertEquals(postResponse.contains("this username or password was not found in our system"), true);
        test.log(LogStatus.INFO, "Token generation failed!");
        return postResponse;

    }

    public static Response verifyAccountWithInvalidAccountDetails() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream("logs\\verifyAccountWithInvalidAccountDetails.txt"); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .pathParam("accountNo", responseForSavingsAccount+"1")
                .when()
                .header("Authorization", postResponse)
                .get(Constants.listAllAccounts_url + "/{accountNo}");
        Assert.assertEquals(response.getBody().prettyPrint().contains("Error"),true);
        test.log(LogStatus.INFO, "Invalid account detail!");
        return response;
    }

}
