package org.example.pojos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserDataReader {
    private static final String JSON_FILE = "src/main/resources/admin-Users.json";
    private static User[] users;
    private static int currentIndex = 0;

    static {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Gson gson = new Gson();
            users = gson.fromJson(reader, User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized User getNextUser() {
        if (users != null && users.length > 0) {
            currentIndex = (currentIndex + 1) % users.length;
            return users[currentIndex];
        }
        return null;
    }
}

