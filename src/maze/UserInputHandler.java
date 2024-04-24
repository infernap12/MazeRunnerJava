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
            int[] dimensions = null;
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
}
