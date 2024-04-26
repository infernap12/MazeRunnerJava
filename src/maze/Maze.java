package maze;

import java.io.Serializable;

public class Maze implements Serializable {
    NodeGraph graph;

    public Maze(int[] dimensions) {
        this.graph = new NodeGraph(dimensions);
    }

    public Maze(int i) {
        this(new int[]{i, i});
    }

    void print(boolean showSolution) {
        int[][] mazeArray = graph.getMazeArray();

        for (int[] widthArray : mazeArray) {
            for (int cell : widthArray) {
                switch (cell) {
                    case 1 -> System.out.print("  ");
                    case 0 -> System.out.print("\u2588\u2588");
                    case 2 -> System.out.print(showSolution ? "//" : "  ");
                }
            }
            System.out.println();
        }
    }
}
