package maze;

public class Main {
    public static void main(String[] args) {
        int[][] maze = new int[10][10];
        String EXAMPLE_MAZE = "1111111111" +
                              "0010101001" +
                              "1010001011" +
                              "1000111000" +
                              "1010000011" +
                              "1010111011" +
                              "1010100011" +
                              "1010111011" +
                              "1010001001" +
                              "1111111111";
        for (int j = 0; j < maze.length; j++) {
            int[] intArr = maze[j];
            for (int i = 0; i < intArr.length; i++) {
                int index = (j * 10) + i;
                maze[j][i] = Character.getNumericValue(EXAMPLE_MAZE.charAt(index));
            }
        }
        for (int i = 0; i < maze.length; i++) {
            int[] intArr = maze[i];
            for (int j = 0; j < intArr.length; j++) {
                int x = intArr[j];
                if (x == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }

            }
            System.out.println();
        }
        //init a basic array
        //with a static maze
        //create methods to conver that to print object
    }
}
