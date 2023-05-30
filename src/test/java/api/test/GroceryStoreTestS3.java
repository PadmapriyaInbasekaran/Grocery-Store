package api.test;

import api.endpoints.Constants;
import api.endpoints.GroceryStoreEndpoints;
import api.reports.ExternalReport;
import api.utilities.PropertiesClass;
import com.aventstack.extentreports.ExtentReports;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.Properties;

public class GroceryStoreTestS3 extends ExternalReport {
    static Properties locationPath = PropertiesClass.readProperty(Constants.properties_file_path);
    @Test(priority = 11)
    public void getAProduct() throws FileNotFoundException {
        GroceryStoreEndpoints.getAProduct();
    }

    @Test(priority = 12, dependsOnMethods = "getAProduct")
    public void createNewCart() throws FileNotFoundException {
        GroceryStoreEndpoints.createNewCart();

    }

    @Test(priority = 13, dependsOnMethods = "createNewCart")
    public void addAnItemToCart() throws FileNotFoundException {
        GroceryStoreEndpoints.addAnItemToCart();

    }

    @Test(priority = 14, dependsOnMethods = "addAnItemToCart")
    public void replaceCartItem() throws FileNotFoundException {
        GroceryStoreEndpoints.replaceCartItem();

    }

    @Test(priority = 15, dependsOnMethods = "replaceCartItem")
    public void getCartItems() throws FileNotFoundException {
        GroceryStoreEndpoints.getCartItems();
        Assert.assertEquals(GroceryStoreEndpoints.quantity.contains(locationPath.getProperty("cartQuantity")), true);

    }

}

