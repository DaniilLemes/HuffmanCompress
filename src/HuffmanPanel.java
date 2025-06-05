import javax.swing.*;
import java.awt.*;

public class HuffmanPanel extends JPanel {
    private HuffmanNode root;
    private final int nodeRadius = 20;
    private final int vGap = 50;

    public HuffmanPanel(HuffmanNode root) {
        this.root = root;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            int panelWidth = getWidth();
            drawNode(g, root, panelWidth / 2, 30, panelWidth / 4);
        }
    }

    private void drawNode(Graphics g, HuffmanNode node, int x, int y, int hGap) {
        g.setColor(Color.BLACK);
        if (node.data != null) {
            g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            String text = String.format("%02X:%d", node.data, node.frequency);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g.drawString(text, x - textWidth / 2, y + fm.getAscent() / 2);
        } else {
            g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            String text = Integer.toString(node.frequency);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g.drawString(text, x - textWidth / 2, y + fm.getAscent() / 2);
        }
        if (node.left != null) {
            int childX = x - hGap;
            int childY = y + vGap;
            g.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
            drawNode(g, node.left, childX, childY, hGap / 2);
            g.drawString("0", (x + childX) / 2 - 5, (y + childY) / 2);
        }
        if (node.right != null) {
            int childX = x + hGap;
            int childY = y + vGap;
            g.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
            drawNode(g, node.right, childX, childY, hGap / 2);
            g.drawString("1", (x + childX) / 2 - 5, (y + childY) / 2);
        }
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
        repaint();
    }
}