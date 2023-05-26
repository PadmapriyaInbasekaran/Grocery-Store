package api.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
        return properties;
    }
}
