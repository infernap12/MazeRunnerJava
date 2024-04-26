package util;

public class HelperUtils {
    public static String get2DArrayPrint(boolean[][] matrix) {
        StringBuilder output = new StringBuilder(new String());
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                output.append(matrix[i][j] ? "  " : "\u2588\u2588"); //█,█
            }
            output.append("\n");
        }
        return output.toString();
    }
}
