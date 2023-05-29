package api.test;

import api.endpoints.GroceryStoreEndpoints;
import api.reports.ExternalReport;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class GroceryStoreTestS2 extends ExternalReport {

    @Test(priority = 1)
    public void createToken() throws FileNotFoundException {
        GroceryStoreEndpoints.createToken();
    }


    @Test(priority = 2)
    public void createOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.createNewCart();
        GroceryStoreEndpoints.getAProduct();
        GroceryStoreEndpoints.addAnItemToCart();
        GroceryStoreEndpoints.createOrder();

    }

    @Test(priority = 3)
    public void getAllOrders() throws FileNotFoundException {
        GroceryStoreEndpoints.getAllOrders();

    }

    @Test(priority = 4)
    public void updateAnOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.updateAnOrder();

    }


    @Test(priority = 5)
    public void getAOrder() throws FileNotFoundException {
        GroceryStoreEndpoints.getAOrder();

    }
}