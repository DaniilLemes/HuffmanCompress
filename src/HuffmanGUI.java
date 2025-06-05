import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;

public class HuffmanGUI extends JFrame {
    private final HuffmanCoding huffman;
    private HuffmanPanel treePanel;
    private JTextArea codeArea;

    public HuffmanGUI() {
        super("Huffman Coding");
        huffman = new HuffmanCoding();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel topPanel = new JPanel();
        JButton chooseButton = new JButton("Choose File");
        JButton compressButton = new JButton("Compress");
        JButton decompressButton = new JButton("Decompress");
        topPanel.add(chooseButton);
        topPanel.add(compressButton);
        topPanel.add(decompressButton);
        add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane();
        codeArea = new JTextArea();
        codeArea.setEditable(false);
        JScrollPane codeScroll = new JScrollPane(codeArea);
        splitPane.setLeftComponent(codeScroll);

        treePanel = new HuffmanPanel(null);
        JScrollPane treeScroll = new JScrollPane(treePanel);
        splitPane.setRightComponent(treeScroll);

        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("All Files", "*"));

        chooseButton.addActionListener((ActionEvent e) -> {
            int ret = fileChooser.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try {
                    huffman.compress(selected, new File(selected.getParent(), selected.getName() + ".huff"));
                    displayCodesAndTree();
                    JOptionPane.showMessageDialog(this, "File compressed: " + selected.getName() + ".huff");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        compressButton.addActionListener((ActionEvent e) -> {
            int ret = fileChooser.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try {
                    huffman.compress(selected, new File(selected.getParent(), selected.getName() + ".huff"));
                    displayCodesAndTree();
                    JOptionPane.showMessageDialog(this, "File compressed: " + selected.getName() + ".huff");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        decompressButton.addActionListener((ActionEvent e) -> {
            int ret = fileChooser.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                if (!selected.getName().endsWith(".huff")) {
                    JOptionPane.showMessageDialog(this, "Please select a .huff file", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String originalName = selected.getName().substring(0, selected.getName().lastIndexOf('.')) + ".orig";
                    huffman.decompress(selected, new File(selected.getParent(), originalName));
                    JOptionPane.showMessageDialog(this, "File decompressed: " + originalName);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void displayCodesAndTree() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Byte, String> entry : huffman.getCodes().entrySet()) {
            sb.append(String.format("%02X: %s\n", entry.getKey(), entry.getValue()));
        }
        codeArea.setText(sb.toString());
        treePanel.setRoot(huffman.getTree().getRoot());
        treePanel.revalidate();
    }

}
