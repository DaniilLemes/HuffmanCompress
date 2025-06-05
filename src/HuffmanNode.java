import java.util.Objects;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public final int frequency;
    public final Byte data; // null for internal nodes
    public HuffmanNode left, right;

    public HuffmanNode(Byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HuffmanNode that = (HuffmanNode) o;
        return frequency == that.frequency && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, frequency);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}