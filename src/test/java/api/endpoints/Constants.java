package api.endpoints;

import java.net.URI;

public class Constants {
    public static String base_url = "http://simple-grocery-store-api.online";
    public static String generate_token = base_url + "/api-clients";
    public static String get_products = base_url + "/products";
    public static String get_product = base_url + "/products/{productId}";
    public static String create_newCart = base_url + "/carts";
    public static String addItemsToCart = base_url + "/carts/{cartId}/items";
    public static String deleteCartItem = base_url + "/carts/{cartId}/items/{itemId}";
    public static String getCartItems = base_url + "/carts/{cartId}/items";
    public static String updateCartItem = base_url + "/carts/{cartId}/items/{itemId}";
    public static String properties_file_path = "src/test/resources/testData.properties";
    public static String createOrder = base_url + "/orders";
    public static String createToken_file_path = "logs\\createToken.txt";
    public static String createOrder_file_path = "logs\\createOrder.txt";
    public static String CreateNewCart_file_path = "logs\\CreateNewCart.txt";
    public static String addItemsToCart_file_path = "logs\\addItemsToCart.txt";
    public static String deleteCartItem_file_path = "logs\\deleteCartItem.txt";
    public static String getCartItems_file_path = "logs\\getCartItems.txt";
    public static String getAProduct_file_path = "logs\\getAProduct.txt";
    public static String listAProducts_file_path = "logs\\listAProducts.txt";
    public static String addAnItemToCart_file_path = "logs\\addAnItemToCart.txt";
    public static String replaceCartItem_file_path = "logs\\replaceCartItem.txt";
    public static String getAllOrders_file_path = "logs\\getAllOrders";
    public static String updateOrder = base_url + "/orders/{orderId}";
    public static String updateOrder_file_path = "logs\\updateOrder";
    public static String getAOrder_file_path = "logs\\getAOrder";
    public static String getAOrder = base_url + "/orders/{orderId}";
    public static String schemaPath = "src/test/resources/schema.json";
}
