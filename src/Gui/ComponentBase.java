package src.Gui;

import javax.swing.*;

public class ComponentBase extends JPanel {
    protected MainWindow main_window;

    public ComponentBase(MainWindow main_window) {
        this.main_window = main_window;
    }

    public void setMainWindow(MainWindow main_window) {
        this.main_window = main_window;
    }

    public MainWindow getMainWindow() {
        return main_window;
    }

    public void swapComponent(ComponentBase newComponent) {
        main_window.setContent(newComponent);
    }
    
    public void swapComponent(ComponentBase newComponent, String title) {
        main_window.setContent(newComponent);
        main_window.setTitle(title);
    }
}
