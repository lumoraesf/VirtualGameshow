package model;

public class User {
    private String username;
    private String password;
    private boolean admin;

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String toFileLine() {
        return username + "|" + password + "|" + admin;
    }

    public static User fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }
        return new User(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
    }
}
