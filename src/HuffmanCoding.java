import java.io.*;
import java.util.*;

public class HuffmanCoding {
    private HuffmanTree tree;
    private Map<Byte, String> codes;

    public void compress(File inputFile, File outputFile) throws IOException {
        FileInputStream fis = new FileInputStream(inputFile);
        Map<Byte, Integer> freqMap = new HashMap<>();
        int b;
        while ((b = fis.read()) != -1) {
            byte by = (byte) b;
            freqMap.put(by, freqMap.getOrDefault(by, 0) + 1);
        }
        fis.close();

        tree = new HuffmanTree();
        tree.buildTree(freqMap);
        codes = tree.getCodes();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
        oos.writeObject(freqMap);

        fis = new FileInputStream(inputFile);
        StringBuilder sb = new StringBuilder();
        while ((b = fis.read()) != -1) {
            byte by = (byte) b;
            sb.append(codes.get(by));
            if (sb.length() >= 8) {
                int byteVal = Integer.parseInt(sb.substring(0, 8), 2);
                oos.write(byteVal);
                sb.delete(0, 8);
            }
        }
        int padding = 0;
        if (sb.length() > 0) {
            padding = 8 - sb.length();
            while (sb.length() < 8) sb.append('0');
            int byteVal = Integer.parseInt(sb.toString(), 2);
            oos.write(byteVal);
        }
        oos.write(padding);
        fis.close();
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public void decompress(File compressedFile, File outputFile) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(compressedFile));
        Map<Byte, Integer> freqMap = (Map<Byte, Integer>) ois.readObject();
        tree = new HuffmanTree();
        tree.buildTree(freqMap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int next;
        List<Byte> dataBytes = new ArrayList<>();
        while ((next = ois.read()) != -1) {
            dataBytes.add((byte) next);
        }
        ois.close();
        int padding = dataBytes.remove(dataBytes.size() - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dataBytes.size(); i++) {
            sb.append(String.format("%8s", Integer.toBinaryString(dataBytes.get(i) & 0xFF)).replace(' ', '0'));
        }
        sb.setLength(sb.length() - padding);

        FileOutputStream fos = new FileOutputStream(outputFile);
        HuffmanNode node = tree.getRoot();
        HuffmanNode current = node;
        for (int i = 0; i < sb.length(); i++) {
            current = sb.charAt(i) == '0' ? current.left : current.right;
            if (current.isLeaf()) {
                fos.write(current.data);
                current = node;
            }
        }
        fos.close();
    }

    public HuffmanTree getTree() {
        return tree;
    }

    public Map<Byte, String> getCodes() {
        return codes;
    }
}