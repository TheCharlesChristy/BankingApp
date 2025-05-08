package src.Gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import src.Gui.Components.LoginPage;

public class MainWindow extends JFrame {
    private JPanel content;

    public MainWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    public void setContent(JPanel newContent) {
        if (content != null) {
            remove(content);
        }
        content = newContent;
        add(content, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JPanel getContent() {
        return content;
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow("Main Window");
        LoginPage loginPage = new LoginPage(window);
        window.setContent(loginPage);
        window.setVisible(true);
    }
}
