package maze;

import java.util.Arrays;
import java.util.Scanner;

public class UserInputHandler {
    static final Scanner SCANNER = new Scanner(System.in);


    public int[] askDimensions() {
        System.out.println("Please, enter the size of a maze, \"y x\" eg. 8 8 for 8x8");
        while (true) {
            String input = SCANNER.nextLine();
            input.split(" ");
            int[] dimensions;
            try {
                dimensions = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();
                if (dimensions[0] < 3 || dimensions[1] < 3) {
                    throw new RuntimeException("Must be greater than 3");
                }
                return dimensions;
            } catch (Exception e) {
                System.out.println("Error, wrong input format");
                throw e;
            }
        }
    }

    public int askDimension() {
        System.out.println("Please, enter the size of a maze");
        while (true) {
            String input = SCANNER.nextLine();
            int dimension;
            try {
                dimension = Integer.parseInt(input);
                if (dimension < 3) {
                    throw new IllegalArgumentException("Must be greater than 3");
                }
                return dimension;
            } catch (NumberFormatException e) {
                System.out.println("Error, wrong input format");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public CommandType askMenu() throws RuntimeException {
        boolean mazeExists = Main.maze != null;
        System.out.println("=== Menu ===\n" +
                           "1. Generate a new maze\n" +
                           "2. Load a maze");
        if (mazeExists) {
            System.out.println("3. Save the maze\n" +
                               "4. Display the maze\n" +
                               "5. Find the escape");
        }
        System.out.println("0. Exit");
        CommandType cmd = null;
        try {
            cmd = CommandType.values()[Integer.parseInt(SCANNER.nextLine())];
            if ((cmd.equals(CommandType.DISPLAY) || cmd.equals(CommandType.SAVE)) && !mazeExists) {
                throw new IllegalArgumentException();
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new RuntimeException("Incorrect option. Please try again");
        }
        return cmd;
    }

    public String askFileName() {
        return SCANNER.nextLine();
    }
}
