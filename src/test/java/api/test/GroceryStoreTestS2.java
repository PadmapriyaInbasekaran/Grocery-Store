package api.test;

import api.endpoints.GroceryStoreEndpoints;
import api.reports.ExternalReport;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class GroceryStoreTestS2 extends ExternalReport {

    @Test(priority = 6)
    public void createToken() throws FileNotFoundException {
        GroceryStoreEndpoints.createToken();
    }


    @Test(priority = 7, dependsOnMethods = "createToken")
    public void createOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.createNewCart();
        GroceryStoreEndpoints.getAProduct();
        GroceryStoreEndpoints.addAnItemToCart();
        GroceryStoreEndpoints.createOrder();

    }

    @Test(priority = 8, dependsOnMethods = "createOrder")
    public void getAllOrders() throws FileNotFoundException {
        GroceryStoreEndpoints.getAllOrders();

    }

    @Test(priority = 9, dependsOnMethods = "getAllOrders")
    public void updateAnOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.updateAnOrder();

    }


    @Test(priority = 10, dependsOnMethods = "updateAnOrder")
    public void getAOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.getAOrder();

    }
}