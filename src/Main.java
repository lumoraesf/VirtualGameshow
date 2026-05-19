import manager.FileManager;
import manager.UserManager;
import ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        FileManager.initializeDataFiles();
        UserManager userManager = new UserManager();
        userManager.ensureDefaultAdmin();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame(userManager).setVisible(true);
            }
        });
    }
}
