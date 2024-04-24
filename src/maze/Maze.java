package maze;

public class Maze {
    NodeGraph graph;

    public Maze(int[] dimensions) {
        this.graph = new NodeGraph(dimensions);
    }

    void print() {
        boolean[][] mazeArray = graph.getMazeArray();
        // stage 1 draw
        for (int i = 0; i < mazeArray.length; i++) {
            boolean[] widthArray = mazeArray[i];
            for (int j = 0; j < widthArray.length; j++) {
                boolean cell = widthArray[j];
                if (cell) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }

            }
            System.out.println();
        }
    }
}
