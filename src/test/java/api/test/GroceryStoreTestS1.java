package api.test;

import api.endpoints.GroceryStoreEndpoints;
import api.reports.ExternalReport;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;

public class GroceryStoreTestS1 extends ExternalReport {

    @Test(priority = 1)
    public void createToken() throws FileNotFoundException {
        GroceryStoreEndpoints.createToken();
    }

    @Test(priority = 2)
    public void listAllProducts() throws FileNotFoundException {

        GroceryStoreEndpoints.listAllProducts();

    }

    @Test(priority = 3)
    public void createNewCart() throws FileNotFoundException {
        GroceryStoreEndpoints.createNewCart();

    }

    @Test(priority = 4)
    public void addItemsToCart() throws FileNotFoundException {
        GroceryStoreEndpoints.addItemsToCart();

    }

    @Test(priority = 5)
    public void deleteCartItem() throws FileNotFoundException {
        GroceryStoreEndpoints.deleteCartItem();

    }

    @Test(priority = 6)
    public void getCartItems() throws FileNotFoundException {
        GroceryStoreEndpoints.getCartItems();

    }
}