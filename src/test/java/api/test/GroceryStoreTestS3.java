package api.test;

import api.endpoints.GroceryStoreEndpoints;
import api.reports.ExternalReport;
import com.aventstack.extentreports.ExtentReports;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class GroceryStoreTestS3 extends ExternalReport {
    @Test(priority = 1)
    public void getAProduct() throws FileNotFoundException {
        GroceryStoreEndpoints.getAProduct();
    }

    @Test(priority = 2)
    public void createNewCart() throws FileNotFoundException {
        GroceryStoreEndpoints.createNewCart();

    }
    @Test(priority = 3)
    public void addAnItemToCart() throws FileNotFoundException {
        GroceryStoreEndpoints.addAnItemToCart();

    }
    @Test(priority = 4)
    public void replaceCartItem() throws FileNotFoundException {
        GroceryStoreEndpoints.replaceCartItem();

    }
    @Test(priority = 5)
    public void getCartItems() throws FileNotFoundException {
        GroceryStoreEndpoints.getCartItems();

    }

}

