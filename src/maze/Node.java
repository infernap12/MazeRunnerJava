package maze;

import java.util.*;
import java.util.stream.Collectors;

public class Node {
    boolean isPassage;
    boolean isExit;
    int y;
    int x;

    Set<Edge> edges = new HashSet<>();
    Set<Edge> passages = new HashSet<>();

//    public void addNeighbour(Node neighbour) {
//        if (neighbour == null) {
//            return;
//        }
//        //calculate the offset
//        int yOffset = neighbour.y - this.y;
//        int xOffset = neighbour.x - this.x;
//
//
//        //add the offset to our edge list, and the neighbours edge list, as we're undirected
//        this.edges.add(new int[]{yOffset, xOffset});
//        neighbour.edges.add(new int[]{-yOffset, -xOffset});
//    }

//    List<Node> getNeighbours() {
//        List<Node> list = new ArrayList<>();
//        for (int[] offset : edges) {
//            Node node = getNode(y + offset[0], x + offset[1]);
//            if (node != null) {
//                list.add(node);
//            }
//        }
//        return Collections.unmodifiableList(list);
//    }

//    @Override
//    public String toString() {
//        String edgesString = edges.stream()
//                .map(Arrays::toString)
//                .collect(Collectors.joining(", "));
//
//        return "Node{" +
//               "isPassage=" + isPassage +
//               ", isExit=" + isExit +
//               ", y=" + y +
//               ", x=" + x +
//               ", edges=" + edgesString +
//               '}';
//    }

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }
}
