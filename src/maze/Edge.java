package maze;

import java.io.Serializable;

public class Edge implements Serializable {
    Node node;
    int[] offset;

    public Edge(Node node, int[] offset) {
        this.node = node;
        this.offset = offset;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append("node=").append(node);
        sb.append(", offset=");
        if (offset == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < offset.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(offset[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
