package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class UserDAO {

    private static UserDAO instance = new UserDAO();
    private static final String USER_FILE = "src/main/resources/users.csv";

    private UserDAO() {

    }

    public static UserDAO getInstance() {
        return instance;
    }

    /**
     * Read the users csv file and return the user list.
     * @return
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(new User(line));
            }
        } catch (IOException e) {
            System.out.println("CSV / user file not found");
        }

        return users;
    }

    /**
     * Save the user list in the users csv file.
     * @param users The user list
     */
    public void saveUsers(final List<User> users) {
        try {
            FileWriter writer = new FileWriter(USER_FILE, false);
            for (User user : users) {
                writer.append(user.toString() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("CSV / user file not found");
        }
    }
}
