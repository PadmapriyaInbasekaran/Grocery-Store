package api.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class PropertiesClass {
    public static Properties properties;

    public static Properties readProperty(String path) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println("File not found in the given location" + e.getMessage());


        } catch (IOException e) {
            System.out.println("Input Output Exception " + e.getMessage());

        }
        // Generate a random email
        String randomEmail = generateRandomEmail();

        // Set the random email value in the properties object
        properties.setProperty("clientEmail", randomEmail);

        // Save the updated properties to the file
        // ...
        return properties;
    }

    private static String generateRandomEmail() {
        String uniqueId = UUID.randomUUID().toString();
        return "random" + uniqueId.substring(0, 8) + "@example.com";
    }


    }
