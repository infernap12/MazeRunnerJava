package maze;

import java.io.Serializable;
import java.util.*;

public class NodeGraph implements Serializable {
    //this would just be a list of every node.
    List<Edge> solution = new ArrayList<>();
    Set<Node> visited = new HashSet<>();
    Map<Node, Edge> precedenceMap = new HashMap<>();

    //my head is swimming with what level of abstraction/implementation to use
    //do i use a list of all nodes? do i stick to using an array?

    //what's the benefits?

    int yDimension;
    int xDimension;
    private Node[][] grid;

    public NodeGraph(int[] dimension) {
        Set<Node> visited = new HashSet<>();
        yDimension = dimension[0];
        xDimension = dimension[1];
        int nodeXDimension = (xDimension - 1) / 2;
        int nodeYDimension = (yDimension - 1) / 2;
        int nodeCount = nodeXDimension * nodeYDimension;

        grid = new Node[nodeYDimension][nodeXDimension];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = new Node(y, x);
            }
        }
        //creates the full map
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Node node = grid[y][x];
//                node.isPassage = true;
                for (int[] dir : directions) {
                    Node edgeNode = getNode(y + dir[0], x + dir[1]);
                    if (edgeNode != null) {
                        node.neighbours.add(new Edge(edgeNode, dir));
                    }
                }
            }
        }
        //creates the maze itself
        Random rand = new Random();
        //pick random node start node
        List<Edge> list = new ArrayList<>();
        Node startNode = grid[rand.nextInt(nodeYDimension)][rand.nextInt(nodeXDimension)];
        Node node = startNode;
        visited.add(node);
        for (Edge edge : node.neighbours) {
            int[] offset = edge.offset;
            int newY = node.y + offset[0];
            int newX = node.x + offset[1];
            Node frontier = getNode(newY, newX);
            if (frontier != null && !visited.contains(frontier)) {
                list.add(new Edge(node, offset));
            }
        }
        while (!list.isEmpty()) {
            //pick random cell
            Edge edge = list.remove(rand.nextInt(list.size()));
            node = edge.node;
            node = getNode(node.y + edge.offset[0], node.x + edge.offset[1]);
            if (visited.contains(node)) {
                continue;
            }
            //set edge as a passage
            edge.node.linkNode(node);
            //set as passage
            visited.add(node);
            for (Edge e : node.neighbours) {
                int[] direction = e.offset;
                int newY = node.y + direction[0];
                int newX = node.x + direction[1];
                Node frontier = getNode(newY, newX);
                if (frontier != null && !visited.contains(frontier)) {
                    list.add(new Edge(node, direction));
                }
            }
        }
        //todo go over generate code, and see where the backwards facing offset is added, and turn it into an add neighbour


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
        //entrance
        Node entrance = grid[rand.nextInt(nodeYDimension)][0];
        entrance.passages.add(new Edge(null, directions[2]));
        //exit
        Node exit = grid[rand.nextInt(nodeYDimension)][nodeXDimension - 1];
        exit.passages.add(new Edge(null, directions[3]));
        exit.isExit = true;
        dfs(entrance);

    }

    private void dfs(Node parent) {
        visited.add(parent);
        for (Edge edge : parent.passages) {
            if (!solution.isEmpty()) {
                return;
            }
            Node next = edge.node;
            if (next == null) {
                continue;
            }
            //omg we're in the end game now
            if (next.isExit) {
                precedenceMap.put(next, new Edge(parent, edge.offset));
                buildSolution(next);
                return;
            }
            if (!visited.contains(next)) {
                precedenceMap.put(next, new Edge(parent, edge.offset));
                dfs(next);
            }
        }
    }

    private void buildSolution(Node exit) {
        Edge edge = new Edge(exit, new int[]{0, 1});
        List<Edge> list = new ArrayList<>();
        while (edge != null) {
            list.add(edge);
            edge = precedenceMap.get(edge.node);
        }
        Collections.reverse(list);
        solution.addAll(list);
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

    public int[][] getMazeArray() {
        int[][] mazeArray = new int[yDimension][xDimension];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                mazeArray[y * 2 + 1][x * 2 + 1] = 1;
                for (Edge edge : grid[y][x].passages) {
                    int[] offset = edge.offset;
                    //if an edge is in the map, then it's a path to an open node
                    int[] scale = translate(y, x, offset);
                    mazeArray[scale[0]][scale[1]] = 1;
                    if (edge.node == null && xDimension % 2 == 0) {
                        scale = translate(y, x, offset);
                        mazeArray[scale[0]][scale[1] + 1] = 2; // extra thick exit wall
                    }
                }
            }
        }
        int[] dir = translate(solution.get(0).node, new int[]{0, -1});
        mazeArray[dir[0]][dir[1]] = 2;
        for (Edge edge : solution) {
            dir = translate(edge);
            mazeArray[dir[0]][dir[1]] = 2;
            dir = translate(edge.node);
            mazeArray[dir[0]][dir[1]] = 2;
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

    /**
     * @return an int array [y, x] coords scaled to reference the maze array
     */
    int[] translate(Edge edge) {
        return translate(edge.node, edge.offset);
    }

    /**
     * @param node node to pull coords from
     * @return an int array [y, x] coords scaled to reference the maze array
     */
    int[] translate(Node node) {
        return translate(node, new int[]{0, 0});
    }

    /**
     * @param node node to pull coords from
     * @param offset offset to reference an edge instead.
     * @return an int array [y, x] coords scaled to reference the maze array
     */
    int[] translate(Node node, int[] offset) {
        return translate(node.y, node.x, offset);
    }

    int[] translate(int y, int x, int[] offset) {
        return new int[]{(y * 2 + 1) + offset[0], (x * 2 + 1) + offset[1]};
    }

}

