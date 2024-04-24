package maze;

import java.util.*;

public class NodeGraph {
    //this would just be a list of every node.
    List<Node> adjList = new ArrayList<>();
    //my head is swimming with what level of abstraction/implementation to use
    //do i use a list of all nodes? do i stick to using an array?

    //what's the benefits?

    int yDimension;
    int xDimension;
    int nodeXDimension;
    int nodeYDimension;
    private Node[][] grid;

    public NodeGraph(int[] dimension) {
        yDimension = dimension[0];
        xDimension = dimension[1];
        nodeXDimension = (xDimension - 1) / 2;
        nodeYDimension = (yDimension - 1) / 2;
        int nodeCount = nodeXDimension * nodeYDimension;

        grid = new Node[nodeYDimension][nodeXDimension];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = new Node(y, x);
            }
        }
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Node node = grid[y][x];
//                node.isPassage = true;
                for (int[] dir : directions) {
                    Node edgeNode = getNode(y + dir[0], x + dir[1]);
                    if (edgeNode != null) {
                        node.edges.add(new Edge(edgeNode, dir));
                    }
                }
            }
        }
        Random rand = new Random();
        //pick random node start node
        List<Edge> list = new ArrayList<>();
        Node startNode = grid[rand.nextInt(nodeYDimension)][rand.nextInt(nodeXDimension)];
        Node node = startNode;
        node.isPassage = true;
        for (Edge edge : node.edges) {
            int[] offset = edge.offset;
            int newY = node.y + offset[0];
            int newX = node.x + offset[1];
            Node frontier = getNode(newY, newX);
            if (frontier != null && !frontier.isPassage) {
                list.add(new Edge(node, offset));
            }
        }
        while (!list.isEmpty()) {
            //pick random cell
            Edge edge = list.remove(rand.nextInt(list.size()));
            node = edge.source;
            node = getNode(node.y + edge.offset[0], node.x + edge.offset[1]);
            if (node.isPassage) {
                continue;
            }
            //set edge as a passage
            edge.source.passages.add(new Edge(node, edge.offset));
            //set as passage
            node.isPassage = true;
            for (Edge e : node.edges) {
                int[] direction = e.offset;
                int newY = node.y + direction[0];
                int newX = node.x + direction[1];
                Node frontier = getNode(newY, newX);
                if (frontier != null && !frontier.isPassage) {
                    list.add(new Edge(node, direction));
                }
            }
        }


        //compute and add frontier cells
//add all edged nodes to list
//        for (int[] direction : directions) {
//            int newY = node.y + direction[0];
//            int newX = node.x + direction[1];
//            Node frontier = getNode(newY, newX);
//            if (frontier != null && !frontier.isPassage) {
//                list.add(frontier);
//            }
//        }
//        while (!list.isEmpty()) {
//            //pick random cell
//            node = list.remove(rand.nextInt(list.size()));
//            //set as passage
//            node.isPassage = true;
//            node.addNeighbour();
//            //compute and add frontier cells
//            //add all edged nodes to list
//            for (int[] direction : directions) {
//                int newY = node.y + direction[0];
//                int newX = node.x + direction[1];
//                Node frontier = getNode(newY, newX);
//                if (frontier != null && !frontier.isPassage) {
//                    list.add(frontier);
//                }
//            }
//        }
        grid[rand.nextInt(nodeYDimension)][0].passages.add(new Edge(null, directions[2]));
        grid[rand.nextInt(nodeYDimension)][nodeXDimension - 1].passages.add(new Edge(null, directions[3]));


    }

    Node getNode(int y, int x) {
        Node node;
        try {
            node = grid[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            node = null;
        }
        return node;
    }

    public boolean[][] getMazeArray() {
        boolean[][] mazeArray = new boolean[yDimension][xDimension];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                mazeArray[y * 2 + 1][x * 2 + 1] = grid[y][x].isPassage;
                for (Edge edge : grid[y][x].passages) {
                    int[] offset = edge.offset;
                    //if an edge is in the map, then it's a path to an open node
                    mazeArray[(y * 2 + 1) + offset[0]][(x * 2 + 1) + offset[1]] = true;
                    if (edge.source == null && xDimension % 2 == 0) {
                        mazeArray[(y * 2 + 1) + offset[0]][(x * 2 + 1) + offset[1] + 1] = true;
                    }
                }
            }
        }
        return mazeArray;
//        boolean[][] mazeArray = new boolean[grid.length][grid[0].length];
//        for (int y = 0; y < mazeArray.length; y++) {
//            for (int x = 0; x < mazeArray[0].length; x++) {
//                Node node = getNode(y, x);
//                System.out.println(node.toString());
//            }
//        }
//        return mazeArray;
    }
}

