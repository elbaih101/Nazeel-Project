package alia.nazeel.pojos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class for working with JSON data.
 *
 * @author Moustafa Elbaih
 */
public class JsonDataTools {


    private static final Logger log = LoggerFactory.getLogger(JsonDataTools.class);

    /**
     * Writes a new value to a specific key in a JSON file.
     *
     * @param filePath The path of the JSON file.
     * @param key       The key whose value needs to be updated.
     * @param value     The new value to be written to the specified key.
     */
    public static void writeValueToJsonFile(String filePath, String key, String value) {
        try (FileReader fileReader = new FileReader(filePath)) {
            // Parse the JSON file into a JSON object
            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();

            // Add or update the value in the JSON object
            jsonObject.addProperty(key, value);

            // Write the modified JSON object back to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                gson.toJson(jsonObject, fileWriter);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Retrieves the value of a specific key from a JSON file.
     *
     * @param filePath The path of the JSON file.
     * @param key       The key whose value needs to be retrieved.
     * @return The value of the specified key in the JSON file.
     */
    public static String getValueFromJsonFile(String filePath, String key) {
        JsonObject jsonObject = null;
        try (FileReader fileReader = new FileReader(filePath)) {
            // Parse the JSON file into a JSON object
            jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            // Get the Key Value

            // Write the modified JSON object back to the file

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        assert jsonObject != null;
        return jsonObject.get(key).getAsString();
    }
}
