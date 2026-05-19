package ui;

import manager.UserManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class RegisterFrame extends JFrame {
    private UserManager userManager;
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel messageLabel;

    public RegisterFrame(UserManager userManager, JFrame loginFrame) {
        this.userManager = userManager;
        this.loginFrame = loginFrame;
        setTitle("Register");
        setSize(380, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildLayout();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        messageLabel = new JLabel(" ");
        JButton registerButton = new JButton("Create Account");
        JButton backButton = new JButton("Back");

        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(new JLabel("Confirm Password"));
        form.add(confirmPasswordField);
        form.add(registerButton);
        form.add(backButton);

        add(new JLabel("Create a New User", JLabel.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> goBack());
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());
        if (username.length() == 0 || password.length() == 0) {
            messageLabel.setText("Username and password are required.");
            return;
        }
        if (!password.equals(confirm)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }
        if (!userManager.registerUser(username, password)) {
            messageLabel.setText("Username already exists.");
            return;
        }
        messageLabel.setText("Account created. You can log in.");
    }

    private void goBack() {
        loginFrame.setVisible(true);
        dispose();
    }
}
