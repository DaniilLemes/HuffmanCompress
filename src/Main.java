import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HuffmanGUI gui = new HuffmanGUI();
            gui.setVisible(true);
        });
    }
}