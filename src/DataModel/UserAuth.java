package DataModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserAuth {
    private static final String FILE_PATH = "src/database/users.csv"; // CSV file path
    private Map<String, User> userDatabase;
    private Map<String, String> fullNameToUsername;  // A map to store full name to username mapping

    public UserAuth() {
        userDatabase = new HashMap<>();
        fullNameToUsername = new HashMap<>();
        UsersFromCSV();
    }

    // Load users from CSV file into memory
    private void UsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String username = data[0];
                    String fullName = data[1];
                    String password = data[2];
                    User user = new User(username, fullName, password);
                    userDatabase.put(username, user);
                    fullNameToUsername.put(fullName, username); // Store full name to username mapping
                    System.out.println("Loaded user: " + username);  // Debugging line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if login is valid
    public boolean login(String username, String password) {
        User user = userDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }

    // Register a new user and save to CSV
    public boolean register(String username, String fullName, String password) {
        // Check if the username already exists
        if (userDatabase.containsKey(username)) {
            return false; // dataModel.User already exists
        }

        // Add the new user to the in-memory database
        User newUser = new User(username, fullName, password);
        userDatabase.put(username, newUser);
        fullNameToUsername.put(fullName, username);  // Store the full name to username mapping

        // Save the new user to the CSV file
        saveUserToCSV(username, fullName, password);
        return true;
    }

    // Get password by full name
    public String getPasswordByFullName(String fullName) {
        String username = fullNameToUsername.get(fullName);
        if (username != null) {
            User user = userDatabase.get(username);
            return user != null ? user.getPassword() : null;
        }
        return null;  // No user found with that full name
    }

    public String getFullname(String username) {
        User user = userDatabase.get(username);
        return user != null ? user.getFullName() : null;
    }

    // Write a new user to the CSV file
    private void saveUserToCSV(String username, String fullName, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(username + "," + fullName + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

