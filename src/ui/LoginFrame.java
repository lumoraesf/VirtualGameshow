package ui;

import manager.UserManager;
import model.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class LoginFrame extends JFrame {
    private UserManager userManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginFrame(UserManager userManager) {
        this.userManager = userManager;
        setTitle("Game Login");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildLayout();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel(" ");
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(loginButton);
        form.add(registerButton);

        add(new JLabel("Pasapalabra and Tic-Tac-Toe", JLabel.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            new RegisterFrame(userManager, this).setVisible(true);
            setVisible(false);
        });
    }

    private void login() {
        User user = userManager.login(usernameField.getText(), new String(passwordField.getPassword()));
        if (user == null) {
            messageLabel.setText("Invalid username or password.");
            return;
        }
        new MainMenuFrame(userManager, user).setVisible(true);
        dispose();
    }
}
