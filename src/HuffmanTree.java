import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Byte, String> codes;

    public void buildTree(Map<Byte, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        if (pq.size() == 1) {
            pq.add(new HuffmanNode(null, 0));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }
        root = pq.poll();
        codes = new HashMap<>();
        buildCodes(root, "");
    }

    private void buildCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf()) {
            codes.put(node.data, code.length() > 0 ? code : "0");
            return;
        }
        buildCodes(node.left, code + '0');
        buildCodes(node.right, code + '1');
    }

    public Map<Byte, String> getCodes() {
        return codes;
    }

    public HuffmanNode getRoot() {
        return root;
    }
}