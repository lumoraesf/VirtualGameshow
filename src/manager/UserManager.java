package manager;

import model.AdminUser;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public void ensureDefaultAdmin() {
        if (!userExists("admin")) {
            FileManager.appendLine(FileManager.USERS_FILE, new AdminUser("admin", "admin123").toFileLine());
        }
    }

    public boolean registerUser(String username, String password) {
        username = clean(username);
        password = clean(password);
        if (username.length() == 0 || password.length() == 0 || userExists(username)) {
            return false;
        }
        FileManager.appendLine(FileManager.USERS_FILE, new User(username, password, false).toFileLine());
        return true;
    }

    public User login(String username, String password) {
        username = clean(username);
        password = clean(password);
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(String username) {
        username = clean(username);
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        List<String> lines = FileManager.readFile(FileManager.USERS_FILE);
        for (String line : lines) {
            User user = User.fromFileLine(line);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replace("|", "");
    }
}
