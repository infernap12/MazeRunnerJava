package maze;

public class Edge {
    boolean isPassage;
    Node source;
    int[] offset;

    public Edge(Node source, int[] offset) {
        this.isPassage = true;
        this.source = source;
        this.offset = offset;
    }
}
