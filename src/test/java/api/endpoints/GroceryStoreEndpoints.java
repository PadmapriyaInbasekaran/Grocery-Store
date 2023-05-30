package api.endpoints;

import api.payload.GroceryStorePOJO;
import api.reports.ExternalReport;
import api.utilities.PropertiesClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GroceryStoreEndpoints extends ExternalReport {
    public static RequestLoggingFilter requestLoggingFilter;
    public static ResponseLoggingFilter responseLoggingFilter;
    public static String accessToken;
    public static String cartId;
    public static List<Integer> productIds;
    public static String itemId;
    public static String orderId;
    public static GroceryStorePOJO data;
    public static String response;
    public static String comment;
    public static String quantity;
    public static String productId;
    public static String productId1;
    public static String productId2;

    static Properties locationPath = PropertiesClass.readProperty(Constants.properties_file_path);

    public static void createToken() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.createToken_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        data.setClientName(locationPath.getProperty("clientName"));
        data.setClientEmail(locationPath.getProperty("clientEmail"));
        accessToken = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .body(data)
                .when()
                .post(Constants.generate_token).jsonPath().getString("accessToken");
        test.log(LogStatus.INFO, "Token generated successfully!");
    }

    public static void listAllProducts() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.listAProducts_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .when()
                .get(Constants.get_products).asString();
        JSONArray jsonArray = new JSONArray(response);
        productIds = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            int idOfProduct = item.getInt("id");
            productIds.add(idOfProduct);
        }
        if (productIds.size() >= 2) {
            productId1 = String.valueOf(productIds.get(0));
            productId2 = String.valueOf(productIds.get(1));
        }
        test.log(LogStatus.INFO, "All products listed successfully!");
    }

    public static int getFirstProduct() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.listAProducts_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .when()
                .get(Constants.get_products).asString();
        JSONArray jsonArray = new JSONArray(response);
        if (jsonArray.length() > 0) {
            JSONObject firstItem = jsonArray.getJSONObject(0);
            return firstItem.getInt("id");
        }
        test.log(LogStatus.INFO, "First product retrieved successfully!");
        return 0;

    }


    public static void getAProduct() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.getAProduct_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        productId = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("productId", getFirstProduct())
                .when()
                .get(Constants.get_product).jsonPath().getString("id");
        test.log(LogStatus.INFO, "Product retrieved successfully!");

    }

    public static void createNewCart() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.CreateNewCart_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        cartId = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .when()
                .post(Constants.create_newCart).jsonPath().getString("cartId");
        test.log(LogStatus.INFO, "Cart created successfully!");
    }

    public static void addItemsToCart() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.addItemsToCart_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        if (productIds.size() >= 2) {
            String productId1 = String.valueOf(productIds.get(0));
            String productId2 = String.valueOf(productIds.get(1));

            // Set the first product ID
            data.setProductId(productId1);
            itemId = given()
                    .filter(requestLoggingFilter)
                    .filter(responseLoggingFilter)
                    .contentType("application/json")
                    .pathParam("cartId", cartId)
                    .body(data)
                    .when()
                    .post(Constants.addItemsToCart)
                    .jsonPath()
                    .getString("itemId");

            // Set the second product ID
            data.setProductId(productId2);
            itemId = given()
                    .filter(requestLoggingFilter)
                    .filter(responseLoggingFilter)
                    .contentType("application/json")
                    .pathParam("cartId", cartId)
                    .body(data)
                    .when()
                    .post(Constants.addItemsToCart)
                    .jsonPath()
                    .getString("itemId");
        } else {
            System.out.println("Insufficient product IDs to add items to cart.");
        }
        test.log(LogStatus.INFO, "Items added to cart successfully!");
    }

    public static void addAnItemToCart() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.addAnItemToCart_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        data.setProductId(productId);
        itemId = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("cartId", cartId)
                .body(data)
                .when()
                .post(Constants.addItemsToCart).jsonPath().getString("itemId");
        test.log(LogStatus.INFO, "Item Added To Cart successfully!");
    }

    public static void deleteCartItem() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.deleteCartItem_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("cartId", cartId)
                .pathParam("itemId", itemId)
                .when()
                .delete(Constants.deleteCartItem);
        test.log(LogStatus.INFO, "Items deleted from Cart successfully!");
    }

    public static String getCartItems() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.getCartItems_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        quantity = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("cartId", cartId)
                .when()
                .get(Constants.getCartItems).jsonPath().getString("quantity");

        test.log(LogStatus.INFO, "Items retrieved from Cart successfully!");
        return quantity;
    }

    public static void replaceCartItem() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.replaceCartItem_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        data.setProductId(productId);
        data.setQuantity(locationPath.getProperty("cartQuantity"));
        given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("cartId", cartId)
                .pathParam("itemId", itemId)
                .body(data)
                .when()
                .put(Constants.updateCartItem);
        test.log(LogStatus.INFO, "Item updated in Cart successfully!");
    }

    public static void createOrder() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.createOrder_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        data.setCustomerName(locationPath.getProperty("customerName"));
        data.setCartId(cartId);
        orderId = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body(data)
                .when()
                .post(Constants.createOrder).jsonPath().getString("orderId");
        test.log(LogStatus.INFO, "Order created successfully!");
    }

    public static void getAllOrders() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.getAllOrders_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        Response response = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .header("Authorization", accessToken)
                .when()
                .get(Constants.createOrder);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(Constants.schemaPath)));
        test.log(LogStatus.INFO, "Orders retrieved successfully!");
    }

    public static void updateAnOrder() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.updateOrder_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        data.setComment(locationPath.getProperty("userComment"));
        given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .header("Authorization", accessToken)
                .when()
                .body(data)
                .patch(Constants.updateOrder);
        test.log(LogStatus.INFO, "xyz commented successfully!");
    }

    public static void getAOrder() throws FileNotFoundException {

        OutputStream outputStream = new FileOutputStream(Constants.getAOrder_file_path); //use your OutputStream that will write where you need it
        PrintStream printStream = new PrintStream(outputStream, true);
        requestLoggingFilter = new RequestLoggingFilter(printStream);
        responseLoggingFilter = new ResponseLoggingFilter(printStream);
        data = new GroceryStorePOJO();
        comment = given()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .contentType("application/json")
                .header("Authorization", accessToken)
                .pathParam("orderId", orderId)
                .when()
                .get(Constants.getAOrder).jsonPath().getString("comment");
        Assert.assertEquals(comment.contains("Commented by xyz!"), true);
        test.log(LogStatus.INFO, "Order retrieved successfully!");
    }


}
