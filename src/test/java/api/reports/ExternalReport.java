package api.reports;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.lang.reflect.Method;

public class ExternalReport {
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        String USER_DIRECTORY = System.getProperty("user.dir");
        String extentPath = USER_DIRECTORY + File.separator + "test-output" + File.separator + "TestFireAPI.html";
        extent = new ExtentReports(extentPath);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        test = extent.startTest((this.getClass().getSimpleName() + "::" + method.getName()), method.getName());
    }

    @AfterMethod
    public void afterMethod() {
        extent.endTest(test);
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
        extent.close();
    }


}
