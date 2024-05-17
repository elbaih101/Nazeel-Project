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

public class JsonDataTools
{


    private static final Logger log = LoggerFactory.getLogger(JsonDataTools.class);

    public static void writeValueToJsonFile(String filePath, String key, String value)
    {
        try (FileReader fileReader = new FileReader(filePath))
        {
            // Parse the JSON file into a JSON object

            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();

            // Add or update the value in the JSON object
            jsonObject.addProperty(key, value);

            // Write the modified JSON object back to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter fileWriter = new FileWriter(filePath))
            {
                gson.toJson(jsonObject, fileWriter);
            }
        } catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
    }

    public static String getValueFromJsonFile(String filePath, String key)
    {

        JsonObject jsonObject = null;
        try (FileReader fileReader = new FileReader(filePath))
        {
            // Parse the JSON file into a JSON object
            jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            // Get the Key Value

            // Write the modified JSON object back to the file

        } catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        assert jsonObject != null;
        return jsonObject.get(key).getAsString();
    }

}
