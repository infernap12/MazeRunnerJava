package maze;

import java.util.Scanner;

public class UserInputHandler {
    static final Scanner SCANNER = new Scanner(System.in);


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
        System.out.println("""
                === Menu ===
                1. Generate a new maze
                2. Load a maze""");
        if (mazeExists) {
            System.out.println("""
                    3. Save the maze
                    4. Display the maze
                    5. Find the escape""");
        }
        System.out.println("0. Exit");
        CommandType cmd;
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
