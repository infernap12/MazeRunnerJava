package maze;

import java.io.Serializable;
import java.util.*;

public class Node implements Serializable {
    boolean isExit;
    int y;
    int x;

    Set<Edge> neighbours = new HashSet<>();
    Set<Edge> passages = new HashSet<>();

    public void linkNode(Node neighbour) {
        if (neighbour == null) {
            return;
        }
        //calculate the offset
        int yOffset = neighbour.y - this.y;
        int xOffset = neighbour.x - this.x;


        //add the offset to our edge list, and the neighbours edge list, as we're undirected
        this.passages.add(new Edge(neighbour, new int[]{yOffset, xOffset}));
        neighbour.passages.add(new Edge(this, new int[]{-yOffset, -xOffset}));
    }

//    List<Node> getNeighbours() {
//        List<Node> list = new ArrayList<>();
//        for (int[] offset : neighbours) {
//            Node node = getNode(y + offset[0], x + offset[1]);
//            if (node != null) {
//                list.add(node);
//            }
//        }
//        return Collections.unmodifiableList(list);
//    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        sb.append((char) ('A' + this.y));
        sb.append(x + 1);
        return sb.toString();
    }

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }
}
