package maze;

import util.SerializationUtils;

import java.io.IOException;

public class Main {
    static UserInputHandler userInput = new UserInputHandler();
    static Maze maze;

    public static void main(String[] args) {

        while (true) {
            CommandType cmd;
            try {
                cmd = userInput.askMenu();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }
            switch (cmd) {
                case EXIT -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                case GENERATE -> {
                    maze = new Maze(userInput.askDimension());
                    maze.print(false);
                }
                case LOAD -> {
                    String fileName = userInput.askFileName();
                    load(fileName);
                }
                case SAVE -> {
                    String fileName = userInput.askFileName();
                    try {
                        SerializationUtils.serialize(maze, fileName);
                    } catch (IOException e) {
                        System.out.printf("Failed to save the maze to file %s%n", fileName);
                    }
                }
                case DISPLAY -> maze.print(false);
                case FIND -> maze.print(true);
            }
            System.out.println();
        }
    }

    private static void load(String fileName) {
        try {
            maze = (Maze) SerializationUtils.deserialize(fileName);
        } catch (IOException e) {
            System.out.printf("The file %s does not exist%n", fileName);
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load the maze. It has an invalid format.");
        }
    }

}
